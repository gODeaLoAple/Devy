package main.java.com.urfu.Devy.database.repositories;

import main.java.com.urfu.Devy.github.GroupWithChatId;
import main.java.com.urfu.Devy.github.RepositoryInfo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GitHubRepository extends Repository{
    private final Logger log = Logger.getLogger("GitHubRepository");

    public boolean addRepository(String groupId, String chatId, RepositoryInfo repository) {
        try (var statement = database.getConnection().createStatement()) {
            if (hasRepository(groupId))
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `github` (`repository`, `name`, `tracking`, `chatId`, `groupId`)
                    VALUES ('%s','%s','%s','%s', %s)
                    """.formatted(repository.getRepositoryName(), repository.getName(), repository.getTracking() ? 1 : 0, chatId, groupId)
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

    public boolean setTracking(String groupId, boolean tracking){
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    UPDATE `github`
                    SET `tracking`='%d'
                    WHERE `groupId`='%s'
                    """.formatted(tracking ? 1 : 0, groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'setTracking'", throwables);
            return false;
        }
    }

    public Collection<GroupWithChatId> getAllTrackingGroups(){
        var result = new ArrayList<GroupWithChatId>();
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT `groupId`, `chatId`, `tracking`
                FROM `github`
                WHERE `tracking`='1';
                """);
            while(data.next()) {
                result.add(new GroupWithChatId(data.getString("groupId"), data.getString("chatId")));
            }
        } catch (SQLException throwables) {
            log.error("On 'getAllTrackingGroups'", throwables);
        }
        return result;
    }
}
