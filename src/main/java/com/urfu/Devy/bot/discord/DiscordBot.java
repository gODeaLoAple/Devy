package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.Main;
import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Objects;

import org.apache.log4j.Logger;

public class DiscordBot extends ListenerAdapter implements Bot {
    private final Logger log;
    private final CommandParser parser;
    private final String token;
    private final HashMap<String, GroupInfo> groups;

    public DiscordBot(String discordToken) {
        token = discordToken;
        parser = new CommandParser("$");
        groups = new HashMap<>();
        log = Logger.getLogger(DiscordBot.class);
    }

    public void start() {
        try { //5k requests per hour is enough?
            var jda = JDABuilder
                    .createDefault(token)
                    .addEventListeners(this)
                    .build()
                    .awaitReady();
            GitHubClient client = new GitHubClient();
            client.setOAuth2Token(Main.getGitHubToken());
            startTrackingOnLoad(jda);
            log.info("Bot started successfully!");
        } catch (InterruptedException | LoginException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    @Override
    public void removeGroup(GroupInfo group) {
        if (!groups.containsKey(group.getId()))
            throw new IllegalArgumentException("The group had not been added.");
        groups.remove(group.getId());
    }

    @Override
    public void addGroup(GroupInfo group) {
        if (groups.containsKey(group.getId()))
            throw new IllegalArgumentException("The group had been added.");
        groups.put(group.getId(), group);
        log.info("Group was added: " + group.getId());
    }

    @Override
    public void handleMessage(String groupId, String senderId, String message)
            throws ParseCommandException {
        if(!groups.containsKey(groupId))
            throw new IllegalArgumentException("The group had not been added.");
        groups.get(groupId).execute(parser.parse(message), senderId);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild())
            onMessageReceivedFromGuild(event);
        else
            onMessageReceivedFromPerson(event);
    }

    private void onMessageReceivedFromGuild(MessageReceivedEvent event) {
        var group = event.getGuild();
        var channel = event.getTextChannel();
        var message = event.getMessage().getContentRaw();
        try {
            if (parser.isCommand(message) && !event.getAuthor().isBot())
                handleMessage(group.getId(), channel.getId(), message);
        }
        catch (ParseCommandException | IllegalArgumentException e) {
            channel.sendMessage(e.getMessage()).queue();
        }
    }

    private void onMessageReceivedFromPerson(MessageReceivedEvent event) {
        final String message = """
                    Hello, I'm Devy - bot for development. 
                    I'm sorry, but I work only in text-channels now.
                    You can create your own text-channel and call me there!""";
        var author = event.getAuthor();
        if (!author.isBot())
            new DiscordUserSender(author).send(message);
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        super.onGuildReady(event);
        var group = new GroupInfo(event.getGuild().getId());
        for (var channel : event.getGuild().getTextChannels()) {
            try {
                group.addSender(new DiscordMessageSender(channel));
            } catch (CommandException e) {
                log.error(e.getMessage());
            }
        }
        addGroup(group);
    }

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        try {
            super.onTextChannelCreate(event);
            var groupId = event.getGuild().getId();
            var channel = event.getChannel();
            var sender = new DiscordMessageSender(channel);
            groups.get(groupId).addSender(sender);
            log.info("Sender %s was added to group %s".formatted(sender.getId(), groupId));
        }
        catch (CommandException e) {
            log.error(e.getMessage());
        }
    }

    public static void startTrackingOnLoad(JDA jda){
        var groups = RepositoryController.getGitHubRepository().getAllTrackingGroups();
        for(var group : groups){
            var guild = jda.getGuildById(group.getGroupId());
            var channel = Objects.requireNonNull(guild).getTextChannelById(group.getChatId());
            GitHubController.startTrackRepository(new GroupInfo(group.getGroupId()), new DiscordMessageSender(channel));
        }
    }
}