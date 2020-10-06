package main.java.com.urfu.Devy.command.parser;

import main.java.com.urfu.Devy.command.CommandData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class CommandParser {

    protected final String prefix;
    protected final Pattern partsExtractionPattern;

    public CommandParser(String prefixLine) {
        prefix = prefixLine;
        partsExtractionPattern = Pattern.compile("\"[^\"]*\"|\\S+", Pattern.CASE_INSENSITIVE);
    }


    public String getPrefix() {
        return prefix;
    }

    public CommandData parse(String line) throws ParseCommandException {
        line = line.strip();
        if (!line.startsWith(prefix))
            throw new ParseCommandException(String.format("The string have to start with %s.", prefix));
        return parseLineWithoutPrefix(line.substring(prefix.length()).strip());
    }

    protected CommandData parseLineWithoutPrefix(String line) throws ParseCommandException {
        if (line.isEmpty())
            throw new ParseCommandException("The string have to include a command.");
        if (line.chars().filter(x -> x == '"').count() % 2 > 0)
            throw new ParseCommandException("Incorrect quotes amount.");
        var parts = collectParts(line);
        return new CommandData(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }

    private String[] collectParts(String line) {
        var collection = new ArrayList<String>();
        var matcher = partsExtractionPattern.matcher(line);
        while (matcher.find())
            collection.add(matcher.group(0).replaceAll("\"", ""));
        return collection.toArray(new String[0]);
    }

}
