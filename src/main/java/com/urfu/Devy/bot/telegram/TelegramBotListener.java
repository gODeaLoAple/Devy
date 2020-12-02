package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBotListener extends TelegramLongPollingBot {

    private final TelegramBot bot;

    public TelegramBotListener(TelegramBot telegramBot) {
        bot = telegramBot;
    }

    @Override
    public String getBotUsername() {
        return "Devy";
    }


    @Override
    public void onUpdateReceived(Update update) {
       if (update.hasMessage()) {
           var message = update.getMessage();
           if (message.hasText()) {
                if (message.isUserMessage())
                    onUserMessageReceived(message);
                else
                    onGroupMessageReceived(message);
           }
       }
    }

    private void onGroupMessageReceived(Message message) {
        var chatId = message.getChatId();
        var sender = new TelegramMessageSender(chatId.toString(), this);
        bot.handleMessage(chatId, sender, message.getText());
    }

    private void onUserMessageReceived(Message message) {
        var chatId = message.getChatId();
        var sender = new TelegramMessageSender(chatId.toString(), this);
        sender.send(Bot.UserWarningMessage);
    }

    @Override
    public String getBotToken() {
        return bot.getToken();
    }



}