package test.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Command.CommandParser;
import main.java.com.urfu.Devy.Command.ParseCommandException;
import org.junit.jupiter.api.Test;

public class ParserTest {

    protected final CommandParser parser = new CommandParser("$");

    @Test
    public void throwExceptionWhenEmptyLine() {
        testThrowParseCommandException("");
    }

    @Test
    public void throwExceptionWhenBlankLine() {
        testThrowParseCommandException(" ");
    }

    @Test
    public void throwExceptionWhenPrefixNotFound() {
        testThrowParseCommandException("help");
    }

    @Test
    public void throwExceptionWhenPrefixWithoutAnyCommand() {
        testThrowParseCommandException(parser.getPrefix());
    }

    @Test
    public void throwExceptionWhenPrefixWithOnlyBlank() {
        testThrowParseCommandException(parser.getPrefix() + " ");
    }

    private void testThrowParseCommandException(String line) {
        Assertions.assertThrows(ParseCommandException.class, () -> parser.parse(line));
    }

    @Test
    public void returnCommandDataWithCommandNameWhenOnlyCommand() {
        Assertions.assertDoesNotThrow(() -> {
            var actual = parser.parse("$help");
            assertEquals(new CommandData("help", new String[0]), actual);
        });
    }

    @Test
    public void returnCommandDataWhenOnlyOneArgument() {
        Assertions.assertDoesNotThrow(() -> {
            var actual = parser.parse("$help 1");
            assertEquals(new CommandData("help", new String[]{"1"}), actual);
        });
    }

    @Test
    public void returnCommandDataWhenMoreThanOneArgument() {
        Assertions.assertDoesNotThrow(() -> {
            var actual = parser.parse("$help 1 2 3");
            assertEquals(new CommandData("help", new String[] {"1", "2", "3"}), actual);
        });
    }

    @Test
    public void returnCommandDataWhenMoreThanTwoSpacesBetweenArguments() {
        Assertions.assertDoesNotThrow(() -> {
            var actual = parser.parse("$help  1   2");
            assertEquals(new CommandData("help", new String[]{"1", "2"}), actual);
        });
    }

    private void assertEquals(CommandData expected, CommandData actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertArrayEquals(expected.getArgs(), actual.getArgs());
    }

}
