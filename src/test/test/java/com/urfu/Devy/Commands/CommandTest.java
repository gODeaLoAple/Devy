package test.java.com.urfu.Devy.Commands;

import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.command.Command;

public abstract class CommandTest {
    protected EmptySender sender;
    protected GroupInfo group;

    protected abstract Command createCommandWithArgs(String[] args);

    protected void assertHandle(String[] arguments, String handledMessage) {
        createCommandWithArgs(arguments).execute();
        sender.assertMessage(handledMessage);
    }
}
