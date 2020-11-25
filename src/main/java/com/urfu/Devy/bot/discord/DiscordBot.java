package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.Group;
import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.NoSuchElementException;

public class DiscordBot extends Bot {
    private final String token;

    public DiscordBot(String discordToken) {
        super("$");
        token = discordToken;
    }

    @Override
    public void start() {
        log.info("Start bot...");
        try {
            JDABuilder
                    .createDefault(token)
                    .addEventListeners(new DiscordBotListener(this))
                    .build()
                    .awaitReady();
            log.info("Bot started successfully!");
        } catch (InterruptedException | LoginException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    public void handleMessage(String guildId, MessageSender sender, String message) throws ParseCommandException {
        super.handleMessage(getGroupOrCreate(guildId), sender, message);
    }

    private Group getGroupOrCreate(String guildId) {
        try {
            return RepositoryController
                    .getGroupRepository()
                    .getGroupByDiscordChatId(guildId);
        } catch (NoSuchElementException e) {
            var group = new Group().setDiscord(guildId);
            addGroup(group);
            return group;
        }
    }

    public void onGuildReady(String guildId) {
        getGroupOrCreate(guildId);
    }

}