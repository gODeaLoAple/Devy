package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.formatter.TextFormatter;
import main.java.com.urfu.Devy.sender.MessageSender;

public abstract class DiscordSender implements MessageSender {
    @Override
    public TextFormatter createFormatter() {
        return new DiscordTextFormatter();
    }
}
