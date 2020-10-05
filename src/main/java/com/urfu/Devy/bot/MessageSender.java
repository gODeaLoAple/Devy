package main.java.com.urfu.Devy.bot;

import net.dv8tion.jda.api.entities.MessageChannel;

public interface MessageSender {
    void send(String message);

    String getId();
}

