package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.GroupChats;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ChatsRepository extends Repository {

    private static final Logger log = Logger.getLogger(ChatsRepository.class.getSimpleName());

    public boolean addChats(GroupChats chats) {
        try (var statement = database.getConnection().createStatement()) {
            if (chats.getGroupId() != 0)
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `chats` (`telegramId`, `discordId`, `groupId`)
                    VALUES (%d, '%s', %d);
                    """.formatted(chats.getTelegramId(), chats.getDiscordId(), chats.getGroupId())
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addGroup'", throwables);
            return false;
        }
    }

    public boolean updateChats(GroupChats chats) {
        try (var statement = database.getConnection().createStatement()) {
            if (chats.getGroupId() == 0)
                return addChats(chats);
            return statement.executeUpdate("""
                    UPDATE `chats` 
                    SET `discordId`='%s',`telegramId`=%d 
                    WHERE `groupId`=%d
                    """.formatted(chats.getDiscordId(), chats.getTelegramId(), chats.getGroupId())
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'updateGroup'", throwables);
            return false;
        }
    }

    public boolean removeChats(GroupChats chats) {
        try (var statement = database.getConnection().createStatement()) {
            if (chats.getGroupId() == 0)
                return false;
            return statement.executeUpdate("""
                    DELETE FROM `chats` 
                    WHERE `idkey`=%d
                    """.formatted(chats.getGroupId())) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeGroup'", throwables);
            return false;
        }
    }

    public GroupChats getGroupChatsByTelegramId(Long id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT * FROM `chats`
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

    public GroupChats getGroupChatsByDiscordChatId(String id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT * FROM `chats`
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

    private GroupChats extractGroupFromResultSet(ResultSet data) throws SQLException {
        return new GroupChats(data.getInt("idkey"))
                .setDiscord(data.getString("discordId"))
                .setTelegram(data.getLong("telegramId"));
    }

    public boolean hasChats(int groupId) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `chats`
                        WHERE `groupId`=%d)
                    """.formatted(groupId)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasGroup'", throwables);
            return false;
        }
    }
}
