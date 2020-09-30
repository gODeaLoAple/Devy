package main.java.com.urfu.Devy.Command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private static final Map<String, Class<? extends Command>> commands = new HashMap<>(){};

    public static void constructCommandsDictionary(){
        var commandClasses = (new Reflections(Command.class)).getSubTypesOf(Command.class);
        for(var command : commandClasses) {
            if (command.isAnnotationPresent(CommandName.class)) {
                var annotation = command.getDeclaredAnnotation(CommandName.class);
                commands.put(annotation.name(), command);
            }
        }
    }

    public static Class<? extends Command> getCommand(String commandName) {
        if (!commands.containsKey(commandName))
            throw new IllegalArgumentException("no such command yet");
        return commands.get(commandName);
    }

    public static Collection<Class<? extends Command>> getAllCommands(){
        return commands.values();
    }

    public static String getCommandNameAndInfo(String commandName) throws IllegalArgumentException{
        var a= commands.get(commandName);
        if(a == null)
            throw new IllegalArgumentException("wrong command: " + commandName);
        return getCommandNameAndInfo(commands.get(commandName));
    }

    public static String getCommandNameAndInfo(Class<? extends Command> command) throws IllegalArgumentException{
        var annotation = command.getDeclaredAnnotation(CommandName.class);
        return annotation.name() + (annotation.info().isEmpty() ? "" : " :: " + annotation.info());
    }
}
