package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.groups.GroupInfo;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class DiscordBot extends ListenerAdapter implements Bot {
    private static final Logger log = Logger.getLogger(DiscordBot.class);
    protected final HashMap<String, GroupInfo> groups = new HashMap<>();
    protected final CommandParser parser;
    protected final String token;

    public DiscordBot(String discordToken) {
        token = discordToken;
        parser = new CommandParser("$");
    }

    public void start() {
        try {
            JDABuilder
                    .createDefault(token)
                    .addEventListeners(this)
                    .build()
                    .awaitReady();
            log.info("bot started successfully");
        } catch (InterruptedException | LoginException e) {
            log.error("error on start");
        }
    }

    @Override
    public void removeGroup(GroupInfo group) {
        if (!groups.containsKey(group.getId()))
            throw new IllegalArgumentException("Группа не была добавлена");
        groups.remove(group.getId());
    }

    @Override
    public void addGroup(GroupInfo group) {
        if (groups.containsKey(group.getId()))
            throw new IllegalArgumentException("Группа уже была добавлена");
        groups.put(group.getId(), group);
    }

    @Override
    public void handleMessage(String groupId, String senderId, String message) throws ParseCommandException {
        if(!groups.containsKey(groupId))
            throw new IllegalArgumentException("Группа не была добавлена");
        groups.get(groupId).execute(parser.parse(message), senderId);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        var group = event.getGuild();
        var channel = event.getTextChannel();
        var message = event.getMessage().getContentRaw();
        try {
            if (message.startsWith(parser.getPrefix()) && !event.getAuthor().isBot())
                handleMessage(group.getId(), channel.getId(), message);
        }
        catch (ParseCommandException | IllegalArgumentException e) {
            channel.sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        super.onGuildReady(event);
        var group = new GroupInfo(event.getGuild().getId());
        for (var channel : event.getGuild().getTextChannels())
            group.addSender(new DiscordMessageSender(channel));
        addGroup(group);
    }

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        super.onTextChannelCreate(event);
        var groupId = event.getGuild().getId();
        var channel = event.getChannel();
        groups.get(groupId).addSender(new DiscordMessageSender(channel));
    }

}