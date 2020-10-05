package test.java.com.urfu.Devy;

import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {

    protected final CommandParser parser = new CommandParser("$");

    @Test
    public void throwExceptionWhenEmptyLine() {
        assertThrowParseCommandException("");
    }

    @Test
    public void throwExceptionWhenBlankLine() {
        assertThrowParseCommandException(" ");
    }

    @Test
    public void throwExceptionWhenPrefixNotFound() {
        assertThrowParseCommandException("help");
    }

    @Test
    public void throwExceptionWhenPrefixWithoutAnyCommand() {
        assertThrowParseCommandException(parser.getPrefix());
    }

    @Test
    public void throwExceptionWhenPrefixWithOnlyBlank() {
        assertThrowParseCommandException(parser.getPrefix() + " ");
    }

    private void assertThrowParseCommandException(String line) {
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

    @Test
    public void returnCommandDataWhenHasStringWithDoubleQuotesArgumentWithSpaces() {
        Assertions.assertDoesNotThrow(() -> {
            var actual = parser.parse("$help \"hello world\"");
            assertEquals(new CommandData("help", new String[] {"hello world"}), actual);
        });
    }

    @Test
    public void throwExceptionWhenSecondDoubleQuoteNotFound() {
        assertThrowParseCommandException("$help \"hello world");
    }

    private void assertEquals(CommandData expected, CommandData actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertArrayEquals(expected.getArgs(), actual.getArgs());
    }

}
