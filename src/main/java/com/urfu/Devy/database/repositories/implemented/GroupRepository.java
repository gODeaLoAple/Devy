package main.java.com.urfu.Devy.database.repositories.implemented;

import javassist.NotFoundException;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.Group;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;


public class GroupRepository extends Repository {

    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    public boolean addGroup(Group group) {
        try (var statement = database.getConnection().createStatement()) {
            if (group.getId() != 0)
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `groups` (`password`, `telegramId`, `discordId`)
                    VALUES ('%s', %d, '%s');
                    """.formatted(group.getPassword(), group.getTelegramId(), group.getDiscordId())
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addGroup'", throwables);
            return false;
        }
    }

    public boolean updateGroup(Group group) {
        try (var statement = database.getConnection().createStatement()) {
            if (group.getId() == 0)
                return addGroup(group);
            return statement.executeUpdate("""
                    UPDATE `groups` 
                    SET `password`='%s',`discordId`='%s',`telegramId`=%d 
                    WHERE `idkey`=%d
                    """.formatted(group.getPassword(), group.getDiscordId(), group.getTelegramId(), group.getId())
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'updateGroup'", throwables);
            return false;
        }
    }

    public boolean removeGroup(Group group) {
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

    public Group getGroupByTelegramChatId(Long id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT * FROM `groups`
                    WHERE `telegramId`=%d
                    """.formatted(id));
            if (!data.next())
                throw new NoSuchElementException();
            return extractGroupFromResultSet(data);
        } catch (SQLException throwables) {
            log.error("On 'getGroupByTelegramChatId'", throwables);
            throw new NoSuchElementException();
        }
    }

    public Group getGroupByDiscordChatId(String id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT * FROM `groups`
                    WHERE `discordId`='%s'
                    """.formatted(id));
            if (!data.next())
                throw new NoSuchElementException();
            return extractGroupFromResultSet(data);
        } catch (SQLException throwables) {
            log.error("On 'getGroupByDiscordChatId'", throwables);
            throw new NoSuchElementException();
        }
    }

    private Group extractGroupFromResultSet(ResultSet data) throws SQLException {
        return new Group(data.getInt("idkey"))
                .setPassword(data.getString("password"))
                .setDiscord(data.getString("discordId"))
                .setTelegram(data.getLong("telegramId"));
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
