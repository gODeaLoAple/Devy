package test.java.com.urfu.Devy.commands;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.group.EmptyGroup;
import main.java.com.urfu.Devy.sender.EmptySender;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandsControllerTest {

    private EmptyGroup group;
    private EmptySender sender;

    @BeforeEach
    public void setUp() {
        group = new EmptyGroup();
        sender = new EmptySender();
    }

    @BeforeAll
    static void init() {
        CommandsController.constructCommandsDictionary();
    }

    @Test
    public void handleContainsHelp() {
        Assertions.assertTrue(CommandsController.hasCommand("help"));
    }

    @Test
    public void handleWhenDoesNotContainsCommand() {
        Assertions.assertFalse(CommandsController.hasCommand("test"));
    }

    @Test
    public void createUnknownCommandIfDidNotFindCommand() {
        Assertions.assertDoesNotThrow(() -> {
            var command = CommandsController
                    .createCommand(group, sender, new CommandData("test", new String[0]));
            Assertions.assertTrue(command.isNull());
        });
    }

    @Test
    public void handleGetCommandShortInfoIfDoesNotContainsCommand() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Assertions.assertFalse(CommandsController.getCommandNameAndShortInfo("test").isEmpty()));
    }

    @Test
    public void handleGetCommandShortInfo() {
        Assertions.assertDoesNotThrow(() ->
                Assertions.assertNotNull(CommandsController.getCommandNameAndShortInfo("help")));
    }

    @Test
    public void handleGetCommandFullInfoIfDoesNotContainsCommand() {
        Assertions.assertThrows(CommandException.class, () ->
                Assertions.assertFalse(CommandsController.getCommandNameAndFullInfo("test").isEmpty()));
    }

    @Test
    public void handleGetCommandFullInfo() {
        Assertions.assertDoesNotThrow(() ->
                Assertions.assertNotNull(CommandsController.getCommandNameAndFullInfo("help")));
    }
}
