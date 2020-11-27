package main.java.com.urfu.Devy.command;

import main.java.com.urfu.Devy.group.EmptyGroup;
import main.java.com.urfu.Devy.sender.EmptySender;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.commands.common.UnknownCommand;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private static final EmptyGroup group = new EmptyGroup();
    private static final EmptySender sender = new EmptySender();
    private static final Logger log = Logger.getLogger(CommandsController.class.getSimpleName());
    private static final Map<String, Class<? extends  Command>> commands = new HashMap<>(){};

    public static void constructCommandsDictionary() {
        var commandClasses = new Reflections(Command.class).getSubTypesOf(Command.class);
        for (var command : commandClasses)
            if (command.isAnnotationPresent(CommandName.class)) {
                log.info("Configuring \"" + command.getSimpleName() + "\"...");
                commands.put(command.getDeclaredAnnotation(CommandName.class).name(), command);
            }
    }

    public static Command createCommand(GroupInfo group, MessageSender sender, CommandData data) {
        try {
            return getCommandClass(data.getName())
                    .getDeclaredConstructor(GroupInfo.class, MessageSender.class, String[].class)
                    .newInstance(group, sender, data.getArgs());
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

    public static String getCommandNameAndShortInfo(String commandName) throws IllegalArgumentException{
        if(!hasCommand(commandName))
            throw new IllegalArgumentException("Command was not found: " + commandName);
        return getCommandNameAndShortInfo(commands.get(commandName));
    }

    protected static String getCommandNameAndShortInfo(Class<? extends Command> command) throws IllegalArgumentException{
        var annotation = command.getDeclaredAnnotation(CommandName.class);
        var info = annotation.info();
        return annotation.name() + (info.isEmpty() ? "" : " :: " + info);
    }

    public static String getCommandNameAndFullInfo(String commandName) {
        if(!hasCommand(commandName))
            throw new IllegalArgumentException("Command was not found: " + commandName);
        return createCommand(group, sender, new CommandData(commandName, new String[0])).extractParametersInfo();
    }
}
