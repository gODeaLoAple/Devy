package main.java.com.urfu.Devy.sender;

import main.java.com.urfu.Devy.formatter.TextFormatter;

public interface MessageSender {

    void send(String message);

    String getId();

    TextFormatter createFormatter();

}

