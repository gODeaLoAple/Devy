package Bot;

import Command.CommandData;

public interface CommandParser {
    CommandData parse(String line);
}

