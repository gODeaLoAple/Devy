package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.discord.DiscordTextFormatter;
import main.java.com.urfu.Devy.formatter.TextFormatter;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramMessageSender implements MessageSender {

    private final TelegramBotListener bot;
    private final String id;

    public TelegramMessageSender(String chatId, TelegramBotListener bot) {
        this.bot = bot;
        this.id = chatId;
    }

    @Override
    public void send(String message) {
        var sendMessageRequest = new SendMessage(id, message);
        try {
            bot.execute(sendMessageRequest);
        } catch (TelegramApiException ignored) { }
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public TextFormatter createFormatter() {
        return new DiscordTextFormatter();
    }
}
