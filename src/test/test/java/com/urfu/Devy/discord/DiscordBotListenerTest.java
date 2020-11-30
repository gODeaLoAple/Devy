package test.java.com.urfu.Devy.discord;

import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.bot.discord.DiscordBotListener;
import main.java.com.urfu.Devy.bot.discord.DiscordUserSender;
import main.java.com.urfu.Devy.sender.EmptySender;
import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import test.java.com.urfu.Devy.common.DatabaseIncludeTest;

public class DiscordBotListenerTest extends DatabaseIncludeTest {

    private DiscordBot bot;
    private DiscordBotListener listener;
    private EmptySender sender;
    @BeforeEach
    public void setUp() {
        bot = Mockito.mock(DiscordBot.class);
        listener = new DiscordBotListener(bot);
    }

    @Test
    public void testOnGuildReadyCreateGroupIfDoesNotExist() {
        var event = Mockito.mock(GuildReadyEvent.class);
        var guild = Mockito.mock(Guild.class);
        Mockito.doReturn(guild).when(event).getGuild();
        Mockito.doReturn("0").when(guild).getId();
        listener.onGuildReady(event);
        Mockito.verify(bot, Mockito.times(1)).getGroupOrCreate("0");
    }

    @Test
    public void testMessageReceivedFromBot() {
        var event = Mockito.mock(MessageReceivedEvent.class);
        var author = Mockito.mock(User.class);
        Mockito.doReturn(false).when(event).isFromGuild();
        Mockito.doReturn(author).when(event).getAuthor();
        Mockito.doReturn(true).when(author).isBot();

        listener.onMessageReceived(event);
        var sender = new DiscordUserSender(author);
        Mockito.verify(bot, Mockito.never()).sendMessage(sender, DiscordBotListener.UserWarningMessage);
    }

    @Test
    public void testMessageReceivedFromUser() {
        var event = Mockito.mock(MessageReceivedEvent.class);
        var author = Mockito.mock(User.class);
        Mockito.doReturn(false).when(event).isFromGuild();
        Mockito.doReturn(author).when(event).getAuthor();
        Mockito.doReturn(false).when(author).isBot();
        Mockito.doReturn(Mockito.mock(RestAction.class)).when(author).openPrivateChannel();

        listener.onMessageReceived(event);
        Mockito.verify(bot, Mockito.times(1)).sendMessage(Mockito.any(MessageSender.class), Mockito.anyString());
    }

    @Test
    public void testMessageReceivedFromGuildWhenBot() {
        var event = getMockedEvent("$help", true);
        listener.onMessageReceived(event);
        Mockito.verify(bot, Mockito.never()).sendMessage(Mockito.any(MessageSender.class), Mockito.anyString());
    }

    @Test
    public void testMessageReceivedFromGuildWhenNotCommand() {
        var event = getMockedEvent("Hello, Devy", false);
        listener.onMessageReceived(event);
        Mockito.verify(bot, Mockito.never()).sendMessage(Mockito.any(MessageSender.class), Mockito.anyString());
    }

    @Test
    public void testMessageReceivedFromGuildWhenCorrectMessage() {
        var event = getMockedEvent("$help", false);
        Mockito.doReturn(true).when(bot).isCommand(Mockito.anyString());
        listener.onMessageReceived(event);
        Mockito.verify(bot, Mockito.times(1))
                .handleMessage(Mockito.anyString(), Mockito.any(MessageSender.class), Mockito.anyString());
    }

    @Test
    public void testSendErrorDescriptionWhenWrongCommand() {
        var event = getMockedEvent("$help", false);
        Mockito.doReturn(false).when(bot).isCommand(Mockito.anyString());
        listener.onMessageReceived(event);
        Mockito.verify(bot, Mockito.never())
                .sendMessage(Mockito.any(MessageSender.class), Mockito.anyString());
    }

    private MessageReceivedEvent getMockedEvent(String authorMessage, boolean authorIsBot) {
        var author = Mockito.mock(User.class);
        Mockito.doReturn(authorIsBot).when(author).isBot();

        var message = Mockito.mock(ReceivedMessage.class);
        Mockito.doReturn(authorMessage).when(message).getContentRaw();

        var guild = Mockito.mock(Guild.class);
        Mockito.doReturn("0").when(guild).getId();

        var channel = Mockito.mock(TextChannel.class);

        Mockito.doReturn(Mockito.mock(MessageAction.class)).when(channel).sendMessage(Mockito.anyString());

        var event = Mockito.mock(MessageReceivedEvent.class);
        Mockito.doReturn(true).when(event).isFromGuild();
        Mockito.doReturn(author).when(event).getAuthor();
        Mockito.doReturn(message).when(event).getMessage();
        Mockito.doReturn(channel).when(event).getTextChannel();
        Mockito.doReturn(guild).when(event).getGuild();

        return event;
    }
}
