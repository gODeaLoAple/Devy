package main.java.com.urfu.Devy.command.commands.common;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.CommandsController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CommandName(name = "help", info="Show all commands")
public class HelpCommand extends Command{
    public HelpCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Parameter(description = "[commandName]")
    private List<String> targetCommand;

    @Override
    public void execute() {
        try {
            validate();

            var info =  targetCommand == null || targetCommand.isEmpty()
                    ? getAllCommandsInfo()
                    : getInfoAboutCommand(targetCommand.get(0));
            sender.send(info);
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }

    }

    private void validate() throws CommandException {
        if (targetCommand != null && targetCommand.size() > 1)
            throw new CommandException("Incorrect count of arguments. Less or equals than 1 expected.");
    }

    private String getInfoAboutCommand(String commandName) {
        return CommandsController.getCommandNameAndFullInfo(commandName);
    }

    private String getAllCommandsInfo() {
        var sb = new StringBuilder();
        var commands = getCommandsSplitByCategory();
        sortCommands(commands);
        for (var category : commands.keySet()) {
            sb.append(category).append(":").append(System.lineSeparator());
            for (var command : commands.get(category))
                sb.append(CommandsController.getCommandNameAndShortInfo(command)).append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }
        return sb.toString().stripTrailing();
    }

    private Map<String, List<String>> getCommandsSplitByCategory() {
        var result = new HashMap<String, List<String>>();
        for (var command : CommandsController.getAllCommands()) {
            var category = getShortPackageName(command.getPackageName());
            if (!result.containsKey(category))
                result.put(category, new ArrayList<>());
            result.get(category).add(command.getAnnotation(CommandName.class).name());
        }
        return result;
    }

    private String getShortPackageName(String fullPackageName) {
        var split = fullPackageName.split("\\.");
        return split[split.length - 1];
    }

    private void sortCommands(Map<String, List<String>> commandsCategories) {
        for (var category : commandsCategories.keySet())
            commandsCategories.get(category).sort(String::compareToIgnoreCase);
    }
}
