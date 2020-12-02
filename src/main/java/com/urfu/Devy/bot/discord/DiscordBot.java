package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import java.util.NoSuchElementException;

public class DiscordBot extends Bot {
    private final String token;

    public DiscordBot(String discordToken) {
        super("$");
        token = discordToken;
    }

    @Override
    public void start(BotBuilder builder) {
        log.info("Start bot...");
        try {
            builder.build(this);
            log.info("Bot started successfully!");
        } catch (BotBuildException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    public void handleMessage(String guildId, MessageSender sender, String message) {
        handleMessage(getGroupOrCreate(guildId), sender, message);
    }

    public GroupInfo getGroupOrCreate(String guildId) {
        try {
            var groupId = RepositoryController
                    .getChatsRepository()
                    .getGroupIdByDiscordChatId(guildId);
            return RepositoryController
                    .getGroupRepository()
                    .getGroupById(groupId);
        } catch (NoSuchElementException e) {
            var group = createGroup();
            group.asChats().create();
            group.asChats().getChats().setDiscordId(guildId);
            return group;
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    public void createIfNotExists(String guildId) {
        getGroupOrCreate(guildId);
    }
}