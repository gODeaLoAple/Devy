package test.java.com.urfu.Devy.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.groups.GetChatsCommand;
import main.java.com.urfu.Devy.group.Group;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

public class GetChatsCommandTest extends CommandTester {

    @BeforeEach
    public void setUp() {
        group = new Group();
        sender = new EmptySender();
    }

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new GetChatsCommand(group, sender, args);
    }

    @Test
    public void handleWhenHasNoChats() {
        assertHandle(new String[0], "Hmm... Strangely, you has no chats.");
    }

    @Test
    public void handleWhenHasOnlyOneChat() {
        handleWhenHasOnlyDiscord();
        handleWhenHasOnlyTelegram();
    }

    private void handleWhenHasOnlyDiscord() {
        group = new Group().setDiscord("discord");
        assertHandle(new String[0], "Discord: discord");
    }

    private void handleWhenHasOnlyTelegram() {
        group = new Group().setTelegram(0L);
        assertHandle(new String[0], "Telegram: 0");
    }

    @Test
    public void handleWhenHasSomeChats() {
        group = new Group()
                .setTelegram(0L)
                .setDiscord("0");
        assertHandle(new String[0],
                        "Telegram: 0" + System.lineSeparator() +
                        "Discord: 0");
    }

}
