package test.java.com.urfu.Devy.telegram;

import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.telegram.TelegramBot;
import main.java.com.urfu.Devy.bot.telegram.TelegramBotBuilder;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import main.java.com.urfu.Devy.sender.EmptySender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Chat;
import test.java.com.urfu.Devy.common.DatabaseIncludeTest;

public class TelegramBotTest extends DatabaseIncludeTest {

    private TelegramBot bot;
    private TelegramBotBuilder builder;
    @BeforeEach
    public void setUp() {
        bot = new TelegramBot("");
        builder = Mockito.mock(TelegramBotBuilder.class);
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
        bot.getGroupOrCreate(0L);
        Assertions.assertTrue(bot.hasGroup(new GroupInfo(0)));
    }

    @Test
    public void testGetGroupOrCreateWhenGroupExists() {
        var group = new GroupInfo(0);
        group.asChats().addChats(new Chats(0));
        group.asChats().getChats().setTelegramId(1L);
        bot.addGroup(group);
        Assertions.assertEquals(group, bot.getGroupOrCreate(1L));
    }

    @Test
    public void testHandleMessage() {
        var sender = new EmptySender();
        Assertions.assertDoesNotThrow(() -> bot.handleMessage(0L, sender, "/help"));
        Assertions.assertFalse(sender.getLastMessage().isEmpty());
    }

    @Test
    public void testGetToken() {
        Assertions.assertEquals(bot.getToken(), "");
    }
}
