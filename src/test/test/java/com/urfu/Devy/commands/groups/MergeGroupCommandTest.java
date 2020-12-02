package test.java.com.urfu.Devy.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.groups.MergeGroupCommand;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.GroupChats;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.commands.CommandTester;

import java.util.NoSuchElementException;

public class MergeGroupCommandTest extends CommandTester {

    @BeforeEach
    public void setUp() {
        sender = new EmptySender();
        group = new GroupInfo(0);
        group.setChats(new GroupChats(group.getId()));
        group.asChats().create();
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
        group.asChats().getChats().setTelegramId(1L);
        RepositoryController.getGroupRepository().addGroup(group);
        assertHandle(new String[] { "-t", "1" }, "You cannot merge on the same platform.");
    }

    @Test
    public void handleWhenUseDiscordWhenGroupFromDiscord() {
        group.asChats().getChats().setDiscordId("");
        RepositoryController.getGroupRepository().addGroup(group);
        assertHandle(new String[] { "-d", "" }, "You cannot merge on the same platform.");
    }

    @Test
    public void handleWhenIncorrectChatIdFormat() {
        assertHandle(new String[] { "-t", "test" }, "Incorrect chat id.");
    }

    @Test
    public void handleWhenCorrectDiscord() {
        var telegram = new GroupInfo(1);
        telegram.asChats().create();

        RepositoryController.getGroupRepository().addGroup(telegram);
        RepositoryController.getGroupRepository().addGroup(group);

        telegram.asChats().getChats().setTelegramId(0L);
        group.asChats().getChats().setDiscordId("");

        assertHandle(new String[] { "-t", "0" }, "Groups merged!");
        Assertions.assertFalse(RepositoryController.getChatsRepository().hasChats(1));
    }

    @Test
    public void handleWhenCorrectTelegram() {
        var discord = new GroupInfo(1);
        discord.asChats().create();

        RepositoryController.getGroupRepository().addGroup(discord);
        RepositoryController.getGroupRepository().addGroup(group);

        discord.asChats().getChats().setDiscordId("");
        group.asChats().getChats().setTelegramId(1L);
        assertHandle(new String[] { "-d", "" }, "Groups merged!");
        Assertions.assertFalse(RepositoryController.getChatsRepository().hasChats(1));
    }

}
