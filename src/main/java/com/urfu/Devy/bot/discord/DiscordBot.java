package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

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

}