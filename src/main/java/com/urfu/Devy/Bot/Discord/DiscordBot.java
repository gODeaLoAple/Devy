package main.java.com.urfu.Devy.Bot.Discord;

import main.java.com.urfu.Devy.Bot.Bot;
import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Parser.CommandParser;
import main.java.com.urfu.Devy.Command.Parser.ParseCommandException;
import main.java.com.urfu.Devy.Goups.GroupInfo;
import main.java.com.urfu.Devy.Main;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscordBot extends ListenerAdapter implements Bot {
    private static final Logger log = Logger.getLogger(Main.class);
    protected final Map<String, HelperBot> helpers = new HashMap<>();
    protected final CommandParser parser;
    protected final String token;

    public DiscordBot(String discordToken) {
        token = discordToken;
        parser = new CommandParser("$");
    }

    public void start(){
        try {
            JDABuilder
                    .createDefault(token)
                    .addEventListeners(this)
                    .build()
                    .awaitReady();
            log.info("bot started");
        } catch (InterruptedException | LoginException e) {
            log.error("EXCEPTION at startBot", e);
        }
    }

    public void execute(GroupInfo group, String line) {
        var groupId = group.getId();
        if (!helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа не найдена");
        try {
            send(helpers.get(groupId).execute(parser.parse(line)));
        }
        catch (ParseCommandException e) {
            send(e.getMessage());
        }
    }

    public void execute(HelperBot helper, String line, MessageChannel channel) {
        try {
            send(helper.execute(parser.parse(line)), channel);
        }
        catch (ParseCommandException e) {
            send(e.getMessage());
        }
    }

    public void send(String message, MessageChannel channel){
        channel.sendMessage(message).queue();
    }
    @Override
    public void send(String message) {
        System.out.println(message);
    }

    @Override
    public void receive(GroupInfo from, String message) {
        execute(from, message);
    }

    @Override
    public void removeGroup(GroupInfo group) {
       var groupId = group.getId();
       if (!helpers.containsKey(groupId))
           throw new IllegalArgumentException("Группа не была добавлена");
       helpers.remove(groupId);
    }

    @Override
    public void addGroup(GroupInfo group) {
        var groupId = group.getId();
        if (helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа уже была добавлена");
        helpers.put(groupId, new HelperBot(group));
    }
    
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event){
        event.getGuild().getId();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        var id = event.getGuild().getId();
        if(!helpers.containsKey(id))
            helpers.put(id, new HelperBot(new GroupInfo(id)));
        execute(helpers.get(id), event.getMessage().getContentRaw(), event.getChannel());
        //var a = event.getChannel();
        /*
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("$ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message>
                    .queue(response /* => Message  -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }*/
    }
}
