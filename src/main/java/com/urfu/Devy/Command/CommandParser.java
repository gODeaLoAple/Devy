package main.java.com.urfu.Devy.Command;

import java.util.Arrays;

public class CommandParser {

    private final String prefix;

    public CommandParser(String prefix) {
        this.prefix = prefix;
    }

    public CommandData parse(String line) {
        line = line.strip().toLowerCase();
        validateLine(line);
        var parts = line.substring(prefix.length()).split(" ");
        validateParts(parts);
        return new CommandData(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }

    private void validateLine(String line) {
        if (line.isEmpty())
            throw new IllegalArgumentException("Строка должна содержать непробельные символы");
        if (!line.startsWith(prefix))
            throw new IllegalArgumentException(String.format("Строка должна начинаться с %s", prefix));
    }

    private void validateParts(String[] parts) {
        if (parts.length == 0)
            throw new IllegalArgumentException("Список аргументов пуст");
    }

}
