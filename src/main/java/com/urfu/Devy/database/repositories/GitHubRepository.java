package main.java.com.urfu.Devy.database.repositories;

import main.java.com.urfu.Devy.github.RepositoryInfo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GitHubRepository extends Repository{
    private final Logger log = Logger.getLogger("GitHubRepository");

    public boolean addRepository(String groupId, RepositoryInfo repository) {
        try (var statement = database.getConnection().createStatement()) {
            if (hasRepository(groupId))
                return false;
            System.out.println(repository.getRepositoryName());
            return statement.executeUpdate("""
                    INSERT INTO `github` (`repository`, `name`, `tracking`, `groupId`)
                    VALUES ('%s','%s','%s','%s')
                    """.formatted(repository.getRepositoryName(), repository.getName(), 0, groupId)
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addRepositoryList'", throwables);
            return false;
        }
    }

    public boolean removeRepository(String groupId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    DELETE FROM `github` 
                    WHERE `groupId`='%s'
                    """.formatted(groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeRepositoryList'", throwables);
            return false;
        }
    }

    public RepositoryInfo getRepository(String groupId) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `name`, `repository`, `tracking`
                    FROM `github`
                    WHERE `groupId`='%s'
                    """.formatted(groupId));
            if (!data.next())
                return null;
            return new RepositoryInfo(data.getString("name"), data.getString("repository"), data.getBoolean("tracking"));
        } catch (SQLException throwables) {
            log.error("On 'getRepository'", throwables);
            return null;
        }
    }

    public Collection<RepositoryInfo> getAllRepositories() {
        var result = new ArrayList<RepositoryInfo>();
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT * `repository`, `name`, `tracking`
                FROM `github`;
                """);
            while(data.next()) {
                result.add(new RepositoryInfo(data.getString("name"), data.getString("repository"), data.getBoolean("tracking")));
            }
        } catch (SQLException throwables) {
            log.error("On 'getAllRepositories'", throwables);
        }
        return result;
    }

    public boolean hasRepository(String groupId) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `github`
                        WHERE `groupId`='%s')
                    """.formatted(groupId)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasRepository'", throwables);
            return false;
        }
    }

    public String getLastCommitDate(String groupId){
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT `lastCommit`
                FROM `github`
                WHERE `groupId`='%s';
                """.formatted(groupId));
            if (!data.next())
                return null;
            return data.getString("lastCommit");

        } catch (SQLException throwables) {
            log.error("On 'getAllRepositories'", throwables);
            return null;
        }
    }

    public boolean setLastCommitDate(String groupId, String date){
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    UPDATE `github`
                    SET `lastCommit`='%s'
                    WHERE `groupId`='%s'
                    """.formatted(date, groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addRepositoryList'", throwables);
            return false;
        }
    }
}
