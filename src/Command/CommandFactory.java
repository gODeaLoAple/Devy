package Command;

import Bot.HelperBot;
import Command.Commands.HelpCommand;
import Command.Commands.PingCommand;
import Command.Commands.UnknownCommand;

public class CommandFactory {
    public static Command create(HelperBot bot, String commandName) {
        return switch (commandName) {
            case "help" -> new HelpCommand(bot);
            case "ping" -> new PingCommand(bot);
            default -> new UnknownCommand(bot);
        };
    }
}
