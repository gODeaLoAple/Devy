package test.java.com.urfu.Devy.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.groups.GetChatsCommand;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import main.java.com.urfu.Devy.group.modules.chats.GroupChats;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

public class GetChatsCommandTest extends CommandTester {

    @BeforeEach
    public void setUp() {
        group = new GroupInfo();
        group.setChats(new GroupChats(group.getId()));
        group.asChats().addChats(new Chats(1));
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
    public void handleWhenHasOnlyDiscord() {
        group.asChats().getChats().setDiscordId("discord");
        assertHandle(new String[0], "Discord: discord");
    }

    @Test
    public void handleWhenHasOnlyTelegram() {
        group.asChats().getChats().setTelegramId(1L);
        assertHandle(new String[0], "Telegram: 1");
    }

    @Test
    public void handleWhenHasSomeChats() {
        var groupChats = group.asChats();
        var chats = groupChats.getChats();
        chats.setTelegramId(1L);
        chats.setDiscordId("0");
        assertHandle(new String[0],
                        "Telegram: 1" + System.lineSeparator() +
                        "Discord: 0");
    }

}
