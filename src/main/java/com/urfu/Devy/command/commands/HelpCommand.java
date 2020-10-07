package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.CommandsController;

@CommandName(name = "help")
public class HelpCommand extends Command{
    public HelpCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Parameter(description = "Enter command name to get info about it")
    private String targetCommand;

    @Override
    public void execute() {
        sender.send(getResult());
    }

    private String getResult() {
        try {
            if (targetCommand == null || targetCommand.isEmpty()) {
                var result = new StringBuilder();
                for (var cmd : CommandsController.getAllCommands()) {
                    result.append(CommandsController.getCommandNameAndInfo(cmd));
                    result.append(System.lineSeparator());
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
