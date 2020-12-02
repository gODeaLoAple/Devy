package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;


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
            builder.build(this);
            log.info("Bot started successfully!");
        } catch (BotBuildException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    public void handleMessage(Long chatId, MessageSender sender, String message) {
        super.handleMessage(getGroupOrCreate(chatId), sender, message);
    }

    public GroupInfo getGroupOrCreate(Long chatId) {
        try {
            var groupId = RepositoryController
                    .getChatsRepository()
                    .getGroupIdByTelegramId(chatId);
            return RepositoryController
                    .getGroupRepository()
                    .getGroupById(groupId);
        } catch (NoSuchElementException e) {
            var group = createGroup();
            group.asChats().create();
            group.asChats().getChats().setTelegramId(chatId);
            return group;
        }
    }

    @Override
    public MessageSender getSenderById(String id){
        return new TelegramMessageSender(id, new TelegramBotListener(this));
    }
}
