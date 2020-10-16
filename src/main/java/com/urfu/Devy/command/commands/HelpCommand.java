package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;

@CommandName(name = "help")
public class HelpCommand extends Command{
    public HelpCommand(GroupInfo group, MessageSender sender, String[] args) throws ParseCommandException {
        super(group, sender, args);
    }

    @Parameter(description = "Enter command name to get info about it")
    private String targetCommand;

    @Override
    public void execute() {
        sender.send(getResult());
    }

    private String getResult() {
        if (targetCommand == null || targetCommand.isEmpty()) {
            var result = new StringBuilder();
            for (var cmd : CommandsController.getAllCommands()) {
                result.append(CommandsController.getCommandNameAndShortInfo(cmd));
                result.append(System.lineSeparator());
            }
            return result.substring(0, result.length() - 1);
        }
        try {
            return CommandsController.getCommandNameAndFullInfo(targetCommand);
        } catch (ParseCommandException e) {
            return "";
        }
    }
}
