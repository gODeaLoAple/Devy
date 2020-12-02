package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.NoSuchElementException;

public class DiscordBot extends Bot {
    private final String token;

    public DiscordBot(String discordToken) {
        super("$");
        token = discordToken;
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

    public void handleMessage(String guildId, MessageSender sender, String message) {
        handleMessage(getGroupOrCreate(guildId), sender, message);
    }

    public GroupInfo getGroupOrCreate(String guildId) {
        try {
            var groupId = RepositoryController
                    .getChatsRepository()
                    .getGroupIdByDiscordChatId(guildId);
            return RepositoryController
                    .getGroupRepository()
                    .getGroupById(groupId);
        } catch (NoSuchElementException e) {
            var group = createGroup();
            group.asChats().addChats();
            group.asChats().getChats().setDiscordId(guildId);
            return group;
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    public void createIfNotExists(String guildId) {
        getGroupOrCreate(guildId);
    }


    @Override
    public MessageSender getSenderById(String id){
        return new DiscordMessageSender(DiscordBotBuilder.getJda().getTextChannelById(id));
    }
}