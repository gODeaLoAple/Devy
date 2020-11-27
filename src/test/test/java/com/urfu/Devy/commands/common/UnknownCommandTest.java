package test.java.com.urfu.Devy.commands.common;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.common.UnknownCommand;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

public class UnknownCommandTest extends CommandTester {

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new UnknownCommand(group, sender, args);
    }

    @Test
    public void handleMessageNoArguments() {
        assertHandle(new String[0], "Unknown command. Try again!");
    }

    @Test
    public void handleMessageWithArguments() {
        assertHandle(new String[] { "arg1" }, "Unknown command. Try again!");
    }

}
