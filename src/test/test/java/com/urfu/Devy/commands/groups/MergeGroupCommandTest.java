package test.java.com.urfu.Devy.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.groups.MergeGroupCommand;
import main.java.com.urfu.Devy.group.Group;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

public class MergeGroupCommandTest extends CommandTester {

    @BeforeEach
    public void setUp() {
        sender = new EmptySender();
        group = new Group();
    }
    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new MergeGroupCommand(group, sender, args);
    }

    @Test
    public void handleWhenNoArguments() {
        assertHandle(new String[0], "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenOnlyOneArgument() {
        assertHandle(new String[] { "test" }, "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenArgumentsWithoutOption() {
        assertHandle(new String[] { "test", "password" }, "Option not found.");
    }

    @Test
    public void handleIncorrectOption() {
        assertHandle(new String[] { "-W", "test", "password" }, "Option not found.");
    }

    @Test
    public void handleWhenBothOptionsUsed() {
        assertHandle(new String[] { "-t", "-d", "test", "password" }, "Use only one platform.");
    }

    @Test
    public void handleWhenUseTheSamePlatform() {
        var alertMessage = "You cannot merge on this platform.";
        handleWhenUseDiscordWhenGroupFromDiscord(alertMessage);
        handleWhenUseTelegramWhenGroupFromTelegram(alertMessage);
    }

    private void handleWhenUseTelegramWhenGroupFromTelegram(String alertMessage) {
        group = new Group().setTelegram(0L);
        assertHandle(new String[] { "-t", "1", "password" }, alertMessage);
    }

    private void handleWhenUseDiscordWhenGroupFromDiscord(String alertMessage) {
        group = new Group().setDiscord("");
        assertHandle(new String[] { "-d", "test", "password" }, alertMessage);
    }

    @Test
    public void handleWhenIncorrectChatIdFormat() {
        assertHandle(new String[] { "-t", "test", "password" }, "Incorrect chat id.");
    }

    @Test
    public void handleWhenIncorrectPassword() {
        group = new Group().setPassword("wrong");
        assertHandle(new String[] { "-t", "1", "password" }, "Access denied. Wrong password.");
    }

    @Test
    public void handleWhenCorrectDiscord() {
        group = new Group().setDiscord("");
        assertHandle(new String[] { "-t", "0", "password" }, "Groups merged!");
    }

    @Test
    public void handleWhenCorrectTelegram() {
        group = new Group().setTelegram(0L);
        assertHandle(new String[] { "-d", "", "password" }, "Groups merged!");
    }

    @Test
    public void handleWhenCorrectWithoutPassword() {
        group = new Group().setTelegram(0L);
        assertHandle(new String[] { "-d", "", "password" }, "Groups merged!");
    }
}
