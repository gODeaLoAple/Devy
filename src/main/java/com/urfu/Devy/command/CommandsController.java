package main.java.com.urfu.Devy.command;

import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.commands.common.UnknownCommand;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private static final Map<String, Class<? extends  Command>> commands = new HashMap<>(){};

    public static void constructCommandsDictionary() {
        var commandClasses = new Reflections(Command.class).getSubTypesOf(Command.class);
        for (var command : commandClasses)
            if (command.isAnnotationPresent(CommandName.class))
                commands.put(command.getDeclaredAnnotation(CommandName.class).name(), command);
    }

    public static Command createCommand(GroupInfo group, MessageSender sender, CommandData data) {
        try {
            return createInstance(getCommandClass(data.getName()), group, sender, data.getArgs());
        }
        catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | IllegalArgumentException
                | NoSuchMethodException e) {
            return new UnknownCommand(group, sender, data.getArgs());
        }
    }

    public static Boolean hasCommand(String command){
        return commands.containsKey(command);
    }

    protected static Class<? extends Command> getCommandClass(String commandName) {
        if (!hasCommand(commandName))
            throw new IllegalArgumentException("Command was not found: " + commandName);
        return commands.get(commandName);
    }

    public static Collection<Class<? extends Command>> getAllCommands(){
        return commands.values();
    }


    public static CommandName getCommandAttribute(String commandName) throws IllegalArgumentException {
        if(!hasCommand(commandName))
            throw new IllegalArgumentException("Command was not found: " + commandName);
        return getCommandAttribute(commands.get(commandName));
    }

    protected static CommandName getCommandAttribute(Class<? extends Command> command) throws IllegalArgumentException {
        return command.getDeclaredAnnotation(CommandName.class);
    }

    public static String getCommandNameAndFullInfo(String commandName) throws CommandException {
        if(!hasCommand(commandName))
            throw new CommandException("Command was not found: " + commandName);
        return createCommand(null, null, new CommandData(commandName, new String[0])).extractParametersInfo();
    }

    public static String getCommandName(Class<? extends Command> commandClass) {
        try {
            return createInstance(commandClass, null, null, new String[0]).getName();
        } catch (Exception e) {
            throw new IllegalArgumentException("Command not found");
        }
    }

    private static Command createInstance(Class<? extends Command> commandClass, GroupInfo group,
                                             MessageSender sender, String[] args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return commandClass
                .getDeclaredConstructor(GroupInfo.class, MessageSender.class, String[].class)
                .newInstance(group, sender, args);
    }
}
