package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.repositories.implemented.ChatsRepository;
import main.java.com.urfu.Devy.group.modules.GroupChats;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MockChatsRepository extends ChatsRepository {

    private final Map<Integer, GroupChats> chats = new HashMap<>();

    @Override
    public boolean addChats(GroupChats chats) {
        this.chats.put(chats.getGroupId(), chats);
        return true;
    }

    @Override
    public boolean updateChats(GroupChats chats) {
        this.chats.replace(chats.getGroupId(), chats);
        return true;
    }

    @Override
    public boolean removeChats(GroupChats chats) {
        this.chats.remove(chats.getGroupId());
        return true;
    }

    @Override
    public GroupChats getGroupChatsByTelegramId(Long id) {
        return chats
                .values()
                .stream()
                .filter(x -> Objects.equals(x.getTelegramId(), id))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public GroupChats getGroupChatsByDiscordChatId(String id) {
        return chats
                .values()
                .stream()
                .filter(x -> Objects.equals(x.getDiscordId(), id))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public boolean hasChats(int groupId) {
        return chats
                .values()
                .stream()
                .anyMatch(x -> x.getGroupId() == groupId);
    }


}
