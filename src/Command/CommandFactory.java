package Command;

import Bot.GroupInfo;
import Bot.HelperBot;

public class CommandFactory {
    public static Command create(HelperBot bot, String commandName) {
        return switch (commandName) {
            case "help" -> new UnknownCommand(bot);
            case "ping" -> new PingCommand(bot);
            default -> new UnknownCommand(bot);
        };
    }
}
