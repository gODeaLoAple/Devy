package test.java.com.urfu.Devy.commands.common;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.command.commands.common.HelpCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

public class HelpCommandTest extends CommandTester {

    @BeforeAll
    static void initController() {
        CommandsController.constructCommandsDictionary();
    }

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new HelpCommand(group, sender, args);
    }

    @Test
    public void handleWhenNoArguments() {
        createCommandWithArgs(new String[0]).execute();
        Assertions.assertFalse(sender.getLastMessage().isEmpty());
    }

    @Test
    public void handleWhenHasTargetCommand() {
        createCommandWithArgs(new String[] {"help"}).execute();
        Assertions.assertFalse(sender.getLastMessage().isEmpty());
    }

    @Test
    public void handleWhenMoreThanOneArgument() {
        assertHandle(new String[] {"arg1", "arg2"}, "Incorrect count of arguments. Less or equals than 1 expected.");
    }
}
