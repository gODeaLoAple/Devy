package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.bot.MessageSender;
import org.junit.jupiter.api.Assertions;

public class EmptySender implements MessageSender {

    private String lastMessage;

    @Override
    public void send(String message) {
        lastMessage = message;
    }

    @Override
    public String getId() {
        return "";
    }

    public void assertMessage(String message) {
        Assertions.assertEquals(lastMessage, message);
    }

}
