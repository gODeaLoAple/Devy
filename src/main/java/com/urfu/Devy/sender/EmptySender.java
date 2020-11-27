package main.java.com.urfu.Devy.sender;

import org.junit.jupiter.api.Assertions;

public class EmptySender implements MessageSender {

    private String lastMessage;

    @Override
    public void send(String message) {
        lastMessage = message;
    }

    public void assertMessage(String message) {
        Assertions.assertEquals(message, lastMessage);
    }

    public String getLastMessage() {
        return lastMessage;
    }

}
