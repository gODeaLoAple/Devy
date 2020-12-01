package main.java.com.urfu.Devy.group.modules.chats;

import main.java.com.urfu.Devy.database.RepositoryController;

public class Chats {

    private final int id;

    public Chats(int id) {
        this.id = id;
    }

    public Chats() {
        this(0);
    }

    public void setDiscordId(String discordId) {
        RepositoryController.getChatsRepository().setDiscord(id, discordId);
    }

    public String getDiscordId() {
        return RepositoryController.getChatsRepository().getDiscord(id);
    }

    public boolean hasDiscord() {
        return getDiscordId() != null;
    }

    public Long getTelegramId() {
        return RepositoryController.getChatsRepository().getTelegram(id);
    }

    public void setTelegramId(Long telegramId) {
        RepositoryController.getChatsRepository().setTelegram(id, telegramId);
    }

    public boolean hasTelegram() {
        var telegramId = getTelegramId();
        return telegramId != null && telegramId != 0L;
    }

    public int getId() {
        return id;
    }
}
