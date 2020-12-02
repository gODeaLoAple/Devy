package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.modules.chats.GroupChats;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ChatsRepository extends Repository {

    private static final Logger log = Logger.getLogger(ChatsRepository.class.getSimpleName());

    public boolean addChats(int groupId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    INSERT INTO `chats` (`groupId`)
                    VALUES ( %d);
                    """.formatted(groupId)
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addChats'", throwables);
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
            log.error("On 'removeChats'", throwables);
            return false;
        }
    }

    public int getGroupIdByTelegramId(Long id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `groupId` FROM `chats`
                    WHERE `telegramId`=%d
                    """.formatted(id));
            if (!data.next())
                throw new NoSuchElementException();
            return data.getInt("groupId");
        } catch (SQLException throwables) {
            log.error("On 'getGroupByTelegramChatId'", throwables);
            throw new NoSuchElementException();
        }
    }

    public int getGroupIdByDiscordChatId(String id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `groupId` FROM `chats`
                    WHERE `discordId`='%s'
                    """.formatted(id));
            if (!data.next())
                throw new NoSuchElementException();
            return data.getInt("groupId");
        } catch (SQLException throwables) {
            log.error("On 'getGroupByDiscordChatId'", throwables);
            throw new NoSuchElementException();
        }
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
            log.error("On 'hasChats'", throwables);
            return false;
        }
    }

    public Chats getChatsByGroupId(int groupId) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `idkey` FROM `chats`
                    WHERE `groupId`=%d
                    """.formatted(groupId));
            if (!data.next())
                throw new NoSuchElementException();
            return new Chats(data.getInt("idkey"));
        } catch (SQLException throwables) {
            log.error("On 'getGroupByTelegramChatId'", throwables);
            throw new NoSuchElementException();
        }
    }

    public boolean setDiscord(int id, String discordId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    UPDATE `chats`
                    SET `discordId`='%s'
                    WHERE `idkey`=%d
                    """.formatted(discordId, id)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'setDiscord'", throwables);
            return false;
        }
    }

    public String getDiscord(int id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `discordId` FROM `chats`
                    WHERE `idkey`=%d
                    """.formatted(id));
            if (!data.next())
                throw new NoSuchElementException();
            return data.getString("discordId");
        } catch (SQLException throwables) {
            log.error("On 'getDiscord'", throwables);
            throw new NoSuchElementException();
        }
    }

    public Long getTelegram(int id) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `telegramId` FROM `chats`
                    WHERE `idkey`=%d
                    """.formatted(id));
            if (!data.next())
                throw new NoSuchElementException();
            return data.getLong("telegramId");
        } catch (SQLException throwables) {
            log.error("On 'getTelegram'", throwables);
            throw new NoSuchElementException();
        }
    }

    public boolean setTelegram(int id, Long telegramId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    UPDATE `chats`
                    SET `telegramId`=%d
                    WHERE `idkey`=%d
                    """.formatted(telegramId, id)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'setTelegram'", throwables);
            return false;
        }
    }
}
