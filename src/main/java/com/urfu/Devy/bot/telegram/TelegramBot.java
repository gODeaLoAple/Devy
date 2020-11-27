package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.NoSuchElementException;


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

    public void handleMessage(Long chatId, MessageSender sender, String message) throws ParseCommandException {
        super.handleMessage(getGroupOrCreate(chatId), sender, message);
    }

    public GroupInfo getGroupOrCreate(Long chatId) {
        try {
            return getGroup(chatId);
        } catch (NoSuchElementException e) {
            var group = new GroupInfo();
            group.asChats().setTelegram(chatId);
            addGroup(group);
            return group;
        }
    }

    public GroupInfo getGroup(Long chatId) {
        var groupId = RepositoryController
                .getChatsRepository()
                .getGroupChatsByTelegramId(chatId)
                .getGroupId();
        return RepositoryController
                .getGroupRepository()
                .getGroupById(groupId);
    }

}
