package main.java.com.urfu.Devy.group.modules;

public class GroupChats extends GroupModule {

    private Long telegramId;
    private String discordId;

    public GroupChats(int groupId) {
        super(groupId);
    }

    public GroupChats() {
        this(0);
    }


    public GroupChats setTelegram(Long telegramChatId) {
        telegramId = telegramChatId;
        return this;
    }

    public GroupChats setDiscord(String discordChannelId) {
        discordId = discordChannelId;
        return this;
    }

    public String getDiscordId() {
        return discordId;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public boolean hasDiscord() {
        return getDiscordId() != null;
    }

    public boolean hasTelegramId() {
        return getTelegramId() != null;
    }

}

