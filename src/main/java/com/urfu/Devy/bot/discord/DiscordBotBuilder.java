package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DiscordBotBuilder implements BotBuilder {
    @Override
    public void build(Bot bot) throws BotBuildException {
        if (bot instanceof DiscordBot) {
            try {
                JDABuilder
                        .createDefault(bot.getToken())
                        .addEventListeners(new DiscordBotListener((DiscordBot)bot))
                        .build()
                        .awaitReady();
            } catch (InterruptedException | LoginException e) {
                throw new BotBuildException(e.getMessage());
            }
        }
        else
            throw new IllegalArgumentException("Bot must extend " + DiscordBot.class);
    }
}
