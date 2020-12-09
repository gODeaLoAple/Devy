package main.java.com.urfu.Devy.command.commands.common;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.formatter.TextFormatter;
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

    @Parameter(description = "[name]")
    private List<String> target;

    @Parameter(names="-c", description = "Show all commands in category. If category omitted, shows all categories.")
    private boolean category;

    @Override
    public void execute() {
        try {
            validate();
            sender.send(category ? getResultCategory() : getResultCommand());
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    private void validate() throws CommandException {
        if (target != null && target.size() > 1)
            throw new CommandException("Incorrect count of arguments. Less or equals than 1 expected.");
    }

    private String getResultCategory() throws CommandException {
        return target == null || target.isEmpty()
                ? getAllCategories()
                : getCommandsByCategory(target.get(0));

    }

    private String getAllCategories() {
        var formatter = sender.createFormatter();
        formatter.raw("Categories:").line();
        for (var category : getCommandsSplitByCategory().keySet())
            formatter.raw("\t").bold(category).raw(System.lineSeparator());
        return formatter.get();
    }

    private String getCommandsByCategory(String category) throws CommandException {
        var categories = getCommandsSplitByCategory();
        if (categories.containsKey(category)) {
            var formatter = sender.createFormatter();
            extractFromCategoryToFormatter(formatter, category, categories.get(category));
            return formatter.get();
        }
        throw new CommandException("Category not found.");
    }
    private void extractFromCategoryToFormatter(TextFormatter formatter, String category, List<String> commands) {
        formatter.bold(category).raw(":").raw(System.lineSeparator());
        for (var command : commands) {
            var data = CommandsController.getCommandAttribute(command);
            var info = data.info();
            var name = data.name();
            formatter.raw("\t").underline(name);
            if (!info.isEmpty())
                formatter.raw(" :: ").cursive(info);
            formatter.raw(System.lineSeparator());
        }
        formatter.raw(System.lineSeparator());
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

    private String getResultCommand() throws CommandException {
        return target == null || target.isEmpty()
                ? getAllCommandsInfo()
                : getInfoAboutCommand(target.get(0));
    }
    private String getInfoAboutCommand(String commandName) throws CommandException {
        return CommandsController.getCommandNameAndFullInfo(commandName);
    }
    private String getAllCommandsInfo() {
        var formatter = sender.createFormatter();
        var commands = getCommandsSplitByCategory();
        sortCommands(commands);
        for (var category : commands.keySet())
            extractFromCategoryToFormatter(formatter, category, commands.get(category));
        return formatter.get().stripTrailing();
    }



}
