package main.java.com.urfu.Devy.bot.discord;

import net.dv8tion.jda.api.entities.MessageChannel;

public class DiscordMessageSender extends DiscordSender {

    protected MessageChannel channel;

    public DiscordMessageSender(MessageChannel discordChannel) {
        channel = discordChannel;
    }

    public void send(String message) {
        if (message.length() > 2000) {
            message = message.substring(0, 2000 - 3) + "...";
        }
        channel.sendMessage(message).queue();
    }

    @Override
    public String getId(){
        return channel.getId();
    }

}
