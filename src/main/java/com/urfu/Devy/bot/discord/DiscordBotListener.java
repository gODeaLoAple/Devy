package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordBotListener extends ListenerAdapter {

    private final DiscordBot bot;
    public static final String UserWarningMessage = """
                    Hello, I'm Devy - bot for development. 
                    I'm sorry, but I work only in text-channels now.
                    You can create your own text-channel and call me there!""";
    public DiscordBotListener(DiscordBot discordBot) {
        bot = discordBot;
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        super.onGuildReady(event);
        bot.getGroupOrCreate(event.getGuild().getId());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild())
            onMessageReceivedFromGuild(event);
        else
            onMessageReceivedFromPerson(event);
    }

    private void onMessageReceivedFromGuild(MessageReceivedEvent event) {
        var sender = new DiscordMessageSender(event.getTextChannel());
        var message = event.getMessage().getContentRaw();
        if (bot.isCommand(message) && !event.getAuthor().isBot())
            bot.handleMessage(event.getGuild().getId(), sender, message);
    }

    private void onMessageReceivedFromPerson(MessageReceivedEvent event) {
        var author = event.getAuthor();
        if (!author.isBot())
            bot.sendMessage(new DiscordUserSender(author), UserWarningMessage);
    }

}
