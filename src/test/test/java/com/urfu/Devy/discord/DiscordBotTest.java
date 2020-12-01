package test.java.com.urfu.Devy.discord;

import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.bot.discord.DiscordBotBuilder;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import test.java.com.urfu.Devy.commands.common.HelpCommandTest;
import test.java.com.urfu.Devy.common.DatabaseIncludeTest;

public class DiscordBotTest extends DatabaseIncludeTest {

    private DiscordBotBuilder builder;
    private DiscordBot bot;
    @BeforeEach
    public void setUp() {
        builder = Mockito.mock(DiscordBotBuilder.class);
        bot = new DiscordBot("");
    }

    @Test
    public void testStartFail() throws BotBuildException {
        Mockito.doThrow(new BotBuildException("")).when(builder).build(bot);
        bot.start(builder);
    }

    @Test
    public void testStartSuccess() {
        bot.start(builder);
    }

    @Test
    public void testGetGroupOrCreateWhenDoesNotExist(){
        bot.getGroupOrCreate("");
        Assertions.assertTrue(bot.hasGroup(new GroupInfo(0)));
    }

    @Test
    public void testGetGroupOrCreateWhenGroupExists() {
        var group = new GroupInfo(0);
        group.asChats().addChats(new Chats(0));
        group.asChats().getChats().setDiscordId("");
        bot.addGroup(group);
        Assertions.assertEquals(group, bot.getGroupOrCreate(""));
    }

    @Test
    public void testHandleMessage() {
        var sender = new EmptySender();
        Assertions.assertDoesNotThrow(() -> bot.handleMessage("", sender, "$help"));
        Assertions.assertFalse(sender.getLastMessage().isEmpty());
    }

    @Test
    public void testGetToken() {
        var bot = new DiscordBot("");
        Assertions.assertEquals("", bot.getToken());
    }
}
