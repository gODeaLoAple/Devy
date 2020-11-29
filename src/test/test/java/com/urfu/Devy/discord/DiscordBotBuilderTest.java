package test.java.com.urfu.Devy.discord;

import main.java.com.urfu.Devy.Main;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.bot.discord.DiscordBotBuilder;
import main.java.com.urfu.Devy.bot.telegram.TelegramBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

public class DiscordBotBuilderTest {

    private DiscordBotBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new DiscordBotBuilder();
    }

    @Disabled("Too long: Uses API")
    @Test
    public void testThrowExceptionWhenWrongToken() {
        Assertions.assertThrows(BotBuildException.class, () -> builder.build(new DiscordBot("")));
    }

    @Test
    public void testThrowExceptionWhenWrongBot() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> builder.build(new TelegramBot("")));
    }

    @Disabled("Too long: Uses API")
    @Test
    public void testBuildSuccess() {
        final String token = Main.loadProperties(Main.PATH_TO_TOKENS).getProperty("discord_token");
        Assertions.assertDoesNotThrow(() -> builder.build(new DiscordBot(token)));
    }
}
