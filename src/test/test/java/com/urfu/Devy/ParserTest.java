package test.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Command.CommandParser;
import main.java.com.urfu.Devy.Command.Commands.ParseCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    protected final CommandParser parser = new CommandParser("$");

    @Test
    public void emptyLine() {
        var thrown = assertThrows(ParseCommandException.class, () -> {
            parser.parse("");
        });
    }
}
