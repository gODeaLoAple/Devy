package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.repositories.implemented.ChatsRepository;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import main.java.com.urfu.Devy.group.modules.chats.GroupChats;
import main.java.com.urfu.Devy.group.modules.GroupModule;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MockChatsRepository extends ChatsRepository {

    private final Map<Integer, Chats> chats = new HashMap<>();
    private final Map<Integer, String> discord = new HashMap<>();
    private final Map<Integer, Long> telegram = new HashMap<>();

    @Override
    public boolean addChats(int groupId) {
        var chats = new Chats(this.chats.size() + 1);
        this.chats.put(groupId, chats);
        return true;
    }

    @Override
    public boolean removeChats(GroupChats chats) {
        this.chats.remove(chats.getGroupId());
        return true;
    }

    @Override
    public int getGroupIdByTelegramId(Long id) {
        for (var key : chats.keySet()) {
            var value = chats.get(key);
            if (Objects.equals(value.getTelegramId(), id))
                return key;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int getGroupIdByDiscordChatId(String id) {
        for (var key : chats.keySet()) {
            var value = chats.get(key);
            if (Objects.equals(value.getDiscordId(), id))
                return key;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasChats(int groupId) {
        return chats.containsKey(groupId);
    }

    @Override
    public Chats getChatsByGroupId(int groupId) {
        return chats.get(groupId);
    }

    private Chats getChatsById(int id) {
        return chats.values().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public boolean setDiscord(int id, String discordId) {
        if (!discord.containsKey(id))
            discord.put(id, discordId);
        else
            discord.replace(id, discordId);
        return true;
    }

    @Override
    public String getDiscord(int id) {
        return discord.get(id);
    }

    @Override
    public Long getTelegram(int id) {
        return telegram.get(id);
    }

    @Override
    public boolean setTelegram(int id, Long telegramId) {
        if (!telegram.containsKey(id))
            telegram.put(id, telegramId);
        else
            telegram.replace(id, telegramId);
        return true;
    }
}
