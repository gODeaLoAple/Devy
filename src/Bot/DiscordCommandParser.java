package Bot;

import Command.CommandData;

public class DiscordCommandParser implements CommandParser {

    private final String prefix;

    public DiscordCommandParser(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public CommandData Parse(String line) {
        line = line.strip();
        if (line.isEmpty())
            throw new IllegalArgumentException("Строка должна содержать непробельные символы");
        if (!line.startsWith(prefix))
            throw new IllegalArgumentException(String.format("Строка должна начинаться с %s", prefix));
        line = line.substring(prefix.length()).toLowerCase();
        if (line.isEmpty())
            throw new IllegalArgumentException("Строка не содержит команду");
        var parts = line.split(" ", 2);
        return new CommandData(parts[0], parts.length > 1 ? parts[1] : "");
    }

}
