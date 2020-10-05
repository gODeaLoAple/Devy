package main.java.com.urfu.Devy.command;

import main.java.com.urfu.Devy.bot.HelperBot;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private static final Map<String, Class<? extends  Command>> commands = new HashMap<>(){};

    public static void constructCommandsDictionary() {
        var commandClasses = new Reflections(Command.class).getSubTypesOf(Command.class);
        for (var command : commandClasses) {
            if (command.isAnnotationPresent(CommandName.class)) {
                var annotation = command.getDeclaredAnnotation(CommandName.class);
                commands.put(annotation.name(), command);
            }
        }
    }

    public static Command CreateCommand(String commandName, HelperBot bot){
        try {
            return getCommandClass(commandName).getDeclaredConstructor(HelperBot.class).newInstance(bot);
        }
        catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | IllegalArgumentException
                | NoSuchMethodException e) {
            return null;
        }
    }

    protected static Class<? extends Command> getCommandClass(String commandName) {
        if (!commands.containsKey(commandName))
            throw new IllegalArgumentException("Команда не найдена");
        return commands.get(commandName);
    }

    public static Collection<Class<? extends Command>> getAllCommands(){
        return commands.values();
    }

    public static String getCommandNameAndInfo(String commandName) throws IllegalArgumentException{
        var command= commands.get(commandName);
        if(command == null)
            throw new IllegalArgumentException("Команда не найдена: " + commandName);
        return getCommandNameAndInfo(command);
    }

    public static String getCommandNameAndInfo(Class<? extends Command> command) throws IllegalArgumentException{
        var annotation = command.getDeclaredAnnotation(CommandName.class);
        return annotation.name() + (annotation.info().isEmpty() ? "" : " :: " + annotation.info());
    }
}
