package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends Bot {
    private final String token;
    private final TelegramBotsApi bot;

    public TelegramBot(String botToken) {
        super("/");
        token = botToken;
        bot = new TelegramBotsApi();
    }

    public void start() {
        log.info("Start bot...");
        ApiContextInitializer.init();
        try {
            bot.registerBot(new TelegramBotListener(this));
            log.info("Bot started successfully!");
        } catch (TelegramApiException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    public String getToken() {
        return token;
    }

}
