package Bot;

import Command.CommandData;

public interface CommandParser {
    CommandData Parse(String line);
}

