package main.java.com.urfu.Devy.command;

import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.commands.UnknownCommand;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
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

    public static Command createCommand(GroupInfo group, MessageSender sender, CommandData data) throws ParseCommandException {
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

    public static String getCommandNameAndShortInfo(Class<? extends Command> command) throws IllegalArgumentException{
        var annotation = command.getDeclaredAnnotation(CommandName.class);
        var info = annotation.info();
        return annotation.name() + (info.isEmpty() ? "" : " :: " + info);
    }

    public static String getCommandNameAndFullInfo(String commandName){
        if(!hasCommand(commandName))
            throw new IllegalArgumentException("Command was not found: " + commandName);
        var annotation = commands.get(commandName).getDeclaredAnnotation(CommandName.class);
        return annotation.name() + annotation.detailedInfo();
    }
}
