package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.entities.MessageChannel;

public class DiscordMessageSender implements MessageSender {

    protected MessageChannel channel;

    public DiscordMessageSender(MessageChannel discordChannel) {
        channel = discordChannel;
    }

    public void send(String message) {
        channel.sendMessage(message).queue();
    }

}
