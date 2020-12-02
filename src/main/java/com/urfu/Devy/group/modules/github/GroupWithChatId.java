package main.java.com.urfu.Devy.group.modules.github;

import main.java.com.urfu.Devy.group.GroupInfo;

public class GroupWithChatId {
    private final GroupInfo group;
    private final String chatId;

    public GroupWithChatId(GroupInfo group, String chatId) {
        this.group = group;
        this.chatId = chatId;
    }

    public GroupInfo getGroup() {
        return group;
    }
    public String getChatId() {
        return chatId;
    }
}

