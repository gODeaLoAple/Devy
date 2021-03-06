package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.modules.github.GroupWithChatId;
import main.java.com.urfu.Devy.group.modules.github.RepositoryInfo;
import main.java.com.urfu.Devy.group.GroupInfo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GitHubRepository extends Repository {
    private final Logger log = Logger.getLogger("GitHubRepository");

    public boolean addRepository(int groupId, String chatId, RepositoryInfo repository) {
        try (var statement = database.getConnection().createStatement()) {
            if (hasRepository(groupId))
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `github` (`repository`, `name`, `tracking`, `groupId`, `chatId`)
                    VALUES ('%s','%s','%s', %d, %s)
                    """.formatted(repository.getRepositoryName(),
                    repository.getName(),
                    repository.isTracking() ? 1 : 0,
                    groupId,
                    chatId)
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addRepository'", throwables);
            return false;
        }
    }

    public boolean removeRepository(int groupId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    DELETE FROM `github` 
                    WHERE `groupId`=%d
                    """.formatted(groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeRepositoryList'", throwables);
            return false;
        }
    }

    public RepositoryInfo getRepository(int groupId) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `name`, `repository`, `tracking`
                    FROM `github`
                    WHERE `groupId`=%d
                    """.formatted(groupId));
            if (!data.next())
                return null;
            return new RepositoryInfo(data.getString("name"), data.getString("repository"), data.getBoolean("tracking"));
        } catch (SQLException throwables) {
            log.error("On 'getRepository'", throwables);
            return null;
        }
    }

    public boolean hasRepository(int groupId) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `github`
                        WHERE `groupId`=%d)
                    """.formatted(groupId)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasRepository'", throwables);
            return false;
        }
    }

    public String getLastCommitDate(int groupId){
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT `lastCommitDate`
                FROM `github`
                WHERE `groupId`=%d;
                """.formatted(groupId));
            if (!data.next())
                return null;
            return data.getString("lastCommitDate");

        } catch (SQLException throwables) {
            log.error("On 'getAllRepositories'", throwables);
            return null;
        }
    }

    public boolean setLastCommitDate(int groupId, String date){
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    UPDATE `github`
                    SET `lastCommitDate`='%s'
                    WHERE `groupId`=%d
                    """.formatted(date, groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addRepositoryList'", throwables);
            return false;
        }
    }

    public boolean setTracking(int groupId, boolean tracking){
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    UPDATE `github`
                    SET `tracking`=%d
                    WHERE `groupId`=%d
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
                SELECT `groupId`,  `chatId`
                FROM `github`
                WHERE `tracking`=1;
                """);
            while(data.next())
                result.add(new GroupWithChatId(
                        RepositoryController.getGroupRepository().getGroupById(data.getInt("groupId")),
                        data.getString("chatId")));
        } catch (SQLException throwables) {
            log.error("On 'getAllTrackingGroups'", throwables);
        }
        return result;
    }

}
