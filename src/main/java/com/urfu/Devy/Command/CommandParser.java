package main.java.com.urfu.Devy.Command;

import java.util.Arrays;

public class CommandParser {

    private final String prefix;

    public CommandParser(String prefix) {
        this.prefix = prefix;
    }


    public String getPrefix() {
        return prefix;
    }

    public CommandData parse(String line) throws ParseCommandException {
        line = line.strip().toLowerCase();
        if (!line.startsWith(prefix))
            throw new ParseCommandException(String.format("Строка должна начинаться с %s", prefix));
        return parseLineWithoutPrefix(line.substring(prefix.length()).strip());
    }

    protected CommandData parseLineWithoutPrefix(String line) throws ParseCommandException {
        if (line.isEmpty())
            throw new ParseCommandException("Строка должна включать в себя команду");
        var parts = line.split("\s+");
        return new CommandData(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }

}
