package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
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
        bot.createIfNotExists(event.getGuild().getId());
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
            bot.sendMessage(new DiscordUserSender(author), Bot.UserWarningMessage);
    }

}
