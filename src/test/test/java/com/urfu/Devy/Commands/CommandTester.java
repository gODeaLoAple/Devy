package test.java.com.urfu.Devy.Commands;

import main.java.com.urfu.Devy.sender.EmptySender;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import test.java.com.urfu.Devy.common.DatabaseIncludeTest;

public abstract class CommandTester extends DatabaseIncludeTest {
    protected EmptySender sender;
    protected GroupInfo group;

    protected abstract Command createCommandWithArgs(String[] args);

    protected void assertHandle(String[] arguments, String handledMessage) {
        createCommandWithArgs(arguments).execute();
        sender.assertMessage(handledMessage);
    }

}
