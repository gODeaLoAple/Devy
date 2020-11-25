package main.java.com.urfu.Devy.github;

public class GroupWithChatId {
    private String groupId;
    private String chatId;

    public GroupWithChatId(String groupId, String chatId) {
        this.groupId = groupId;
        this.chatId = chatId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
