package main.java.com.urfu.Devy.Command.Commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Command;
import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Command.CommandName;
import main.java.com.urfu.Devy.Command.CommandsController;

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
                return result.toString().substring(0, result.length() - 1);
            }
            return CommandsController.getCommandNameAndInfo(targetCommand);
        }
        catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
}
