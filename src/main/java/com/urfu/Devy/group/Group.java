package main.java.com.urfu.Devy.group;

public class Group extends GroupInfo {

    private Long telegramId;
    private String discordId;
    private String password;

    public Group(int id) {
        super(id);
    }

    public Group() {
        super(0);
    }

    public Group setTelegram(Long telegramChatId) {
        telegramId = telegramChatId;
        return this;
    }

    public Group setDiscord(String discordChannelId) {
        discordId = discordChannelId;
        return this;
    }

    public Group setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDiscordId() {
        return discordId;
    }

    public Long getTelegramId() {
        return telegramId;
    }


    public String getPassword() {
        return password;
    }

    public boolean hasDiscord() {
        return getDiscordId() != null;
    }

    public boolean hasTelegramId() {
        return getTelegramId() != null && getTelegramId() != 0L;
    }

    public void setId(int id) {
        this.id = id;
    }
}
