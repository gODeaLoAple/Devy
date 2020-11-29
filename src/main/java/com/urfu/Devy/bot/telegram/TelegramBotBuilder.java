package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TelegramBotBuilder implements BotBuilder {
    @Override
    public void build(Bot bot) throws BotBuildException {
        if (bot instanceof TelegramBot) {
            try {
                ApiContextInitializer.init();
                new TelegramBotsApi()
                        .registerBot(new TelegramBotListener((TelegramBot) bot));
            } catch (TelegramApiRequestException e) {
                throw new BotBuildException(e.getMessage());
            }
        }
        else
            throw new BotBuildException("Bot must extends " + TelegramBot.class.getName());
    }
}
