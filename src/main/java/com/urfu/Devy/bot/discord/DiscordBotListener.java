package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.group.GroupInfo;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordBotListener extends ListenerAdapter {

    private final DiscordBot bot;

    public DiscordBotListener(DiscordBot discordBot) {
        bot = discordBot;
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        super.onGuildReady(event);
        var group = new GroupInfo(event.getGuild().getId());
        bot.addGroup(group);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild())
            onMessageReceivedFromGuild(event);
        else
            onMessageReceivedFromPerson(event);
    }

    private void onMessageReceivedFromGuild(MessageReceivedEvent event) {
        var channel = event.getTextChannel();
        var sender = new DiscordMessageSender(channel);
        try {
            var group = event.getGuild();
            var message = event.getMessage().getContentRaw();
            if (bot.isCommand(message) && !event.getAuthor().isBot())
                bot.handleMessage(group.getId(), sender, message);
        }
        catch (ParseCommandException | IllegalArgumentException e) {
            sender.send(e.getMessage());
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
}
