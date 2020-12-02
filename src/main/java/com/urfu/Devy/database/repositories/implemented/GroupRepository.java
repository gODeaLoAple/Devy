package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.GroupChats;
import main.java.com.urfu.Devy.group.modules.github.GroupGithub;
import main.java.com.urfu.Devy.group.modules.todo.GroupTodo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public class GroupRepository extends Repository {

    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    public GroupInfo getGroupById(int groupId) {
        return new GroupInfo(groupId)
                .setChats(new GroupChats(groupId))
                .setGithub(new GroupGithub(groupId))
                .setTodo(new GroupTodo(groupId));
    }

    public boolean removeGroup(GroupInfo group) {
        try (var statement = database.getConnection().createStatement()) {
            if (group.getId() == 0)
                return false;
            return statement.executeUpdate("""
                    DELETE FROM `groups` 
                    WHERE `idkey`=%d
                    """.formatted(group.getId())) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeGroup'", throwables);
            return false;
        }
    }

    public boolean addGroup(GroupInfo group) {
        var sql = "INSERT INTO `groups` VALUES ()";
        try (var statement = database.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (!keys.next())
                return false;
            var id = keys.getInt(1);
            group.setId(id);
            return true;
        } catch (SQLException throwables) {
            log.error("On 'addGroup'", throwables);
            return false;
        }
    }

    public boolean hasGroup(int id) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `groups`
                        WHERE `idkey`=%d)
                    """.formatted(id)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasGroup'", throwables);
            return false;
        }
    }
}
