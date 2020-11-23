package main.java.com.urfu.Devy.database.repositories;

import main.java.com.urfu.Devy.github.RepositoryInfo;
import main.java.com.urfu.Devy.todo.ToDo;
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
                    INSERT INTO `github` (`repository`, `name`, `groupId`)
                    VALUES ('%s','%s','%s')
                    """.formatted(repository.getRepositoryName(), repository.getName(), groupId)
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
                    SELECT `name`, `repository`
                    FROM `github`
                    WHERE `groupId`='%s'
                    """.formatted(groupId));
            if (!data.next())
                return null;
            return new RepositoryInfo(data.getString("name"), data.getString("repository"));
        } catch (SQLException throwables) {
            log.error("On 'getRepository'", throwables);
            return null;
        }
    }

    public Collection<RepositoryInfo> getAllRepositories(String groupId) {
        var result = new ArrayList<RepositoryInfo>();
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT `repository`, `name`
                FROM `github`
                WHERE `groupId`='%s';
                """.formatted(groupId));
            while(data.next()) {
                result.add(new RepositoryInfo(data.getString("name"), data.getString("repository")));
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
}d
