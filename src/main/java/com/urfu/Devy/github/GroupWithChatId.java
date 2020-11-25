package main.java.com.urfu.Devy.github;

public class GroupWithChatId {
    private final String groupId;
    private final String chatId;

    public GroupWithChatId(String groupId, String chatId) {
        this.groupId = groupId;
        this.chatId = chatId;
    }

    public String getGroupId() {
        return groupId;
    }
    public String getChatId() {
        return chatId;
    }
}
