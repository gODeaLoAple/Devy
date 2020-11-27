package test.java.com.urfu.Devy.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.groups.MergeGroupCommand;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.GroupChats;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

public class MergeGroupCommandTest extends CommandTester {

    @BeforeEach
    public void setUp() {
        sender = new EmptySender();
        group = new GroupInfo(0);
        group.setChats(new GroupChats());
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
    public void handleWhenArgumentsWithoutOption() {
        assertHandle(new String[] { "test" }, "Option not found.");
    }

    @Test
    public void handleIncorrectOption() {
        assertHandle(new String[] { "-W", "test" }, "Option not found.");
    }

    @Test
    public void handleWhenBothOptionsUsed() {
        assertHandle(new String[] { "-t", "-d", "test" }, "Use only one platform.");
    }

    @Test
    public void handleWhenUseTelegramWhenGroupFromTelegram() {
        group.asChats().setTelegram(0L);
        assertHandle(new String[] { "-t", "1" }, "You cannot merge on this platform.");
    }

    @Test
    public void handleWhenUseDiscordWhenGroupFromDiscord() {
        group.asChats().setDiscord("");
        assertHandle(new String[] { "-d", "test" }, "You cannot merge on this platform.");
    }

    @Test
    public void handleWhenIncorrectChatIdFormat() {
        assertHandle(new String[] { "-t", "test" }, "Incorrect chat id.");
    }

    @Test
    public void handleWhenCorrectDiscord() {
        group.asChats().setDiscord("");
        assertHandle(new String[] { "-t", "0" }, "Groups merged!");
    }

    @Test
    public void handleWhenCorrectTelegram() {
        group.asChats().setTelegram(0L);
        assertHandle(new String[] { "-d", "" }, "Groups merged!");
    }

    @Test
    public void handleWhenCorrectWithoutPassword() {
        group.asChats().setTelegram(0L);
        assertHandle(new String[] { "-d", "" }, "Groups merged!");
    }
}
