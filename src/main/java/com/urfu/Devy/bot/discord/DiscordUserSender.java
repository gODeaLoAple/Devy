package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.sender.MessageSender;

public class DiscordNotificationSender implements MessageSender {

    private final String id;
    private final DiscordMessageSender[] senders;

    public DiscordNotificationSender(String id, DiscordMessageSender[] senders) {
        this.id = id;
        this.senders = senders;
    }

    @Override
    public void send(String message) {
        for (var sender : senders)
            sender.send(message);
    }

    @Override
    public String getId() {
        return id;
    }
}
