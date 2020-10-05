package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.CommandsController;

@CommandName(name = "help")
public class HelpCommand extends Command{
    public HelpCommand(HelperBot bot) {
        super(bot);
    }

    @Parameter(description = "enter command name to get info about it")
    private String targetCommand;

    @Override
    public String execute(CommandData command) {
        parseArgs(command);
        try {
            if (targetCommand == null || targetCommand.isEmpty()) {
                var result = new StringBuilder();
                for (var cmd : CommandsController.getAllCommands()) {
                    result.append(CommandsController.getCommandNameAndInfo(cmd));
                    result.append("\n");
                }
                return result.substring(0, result.length() - 1);
            }
            return CommandsController.getCommandNameAndInfo(targetCommand);
        }
        catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
}
