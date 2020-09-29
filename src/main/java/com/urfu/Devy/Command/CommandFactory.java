package main.java.com.urfu.Devy.Command;

import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Commands.HelpCommand;
import main.java.com.urfu.Devy.Command.Commands.PingCommand;
import main.java.com.urfu.Devy.Command.Commands.UnknownCommand;

public class CommandFactory {
    public static Command create(HelperBot bot, String commandName) {
        return switch (commandName) {
            case "help" -> new HelpCommand(bot);
            case "ping" -> new PingCommand(bot);
            default -> new UnknownCommand(bot);
        };
    }
}
