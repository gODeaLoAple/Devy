package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.security.auth.login.LoginException;
import java.util.NoSuchElementException;


public class TelegramBot extends Bot {
    private final String token;

    public TelegramBot(String botToken) {
        super("/");
        token = botToken;
    }

    @Override
    public void start(BotBuilder builder) {
        log.info("Start bot...");
        try {
            builder.build(this, token);
            log.info("Bot started successfully!");
        } catch (BotBuildException e) {
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
            var groupId = RepositoryController
                    .getChatsRepository()
                    .getGroupChatsByTelegramId(chatId)
                    .getGroupId();
            return RepositoryController
                    .getGroupRepository()
                    .getGroupById(groupId);
        } catch (NoSuchElementException e) {
            var group = new GroupInfo();
            group.asChats().setTelegram(chatId);
            addGroup(group);
            return group;
        }
    }

}
