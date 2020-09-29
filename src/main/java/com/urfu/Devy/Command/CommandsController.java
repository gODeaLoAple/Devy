package main.java.com.urfu.Devy.Command;

import main.java.com.urfu.Devy.Command.Commands.HelpCommand;
import main.java.com.urfu.Devy.Command.Commands.PingCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsController {
    private static final Map<String, Class<? extends Command>> commands = new HashMap<>(){};

    public static void constructCommandsDictionary(){
        //var commandClasses = (new Reflections(Parent.class)).getSubTypesOf(Parent.class);
        //var a = 0; //.getSubTypesOf(Command.class);
        commands.put("ping", PingCommand.class);
        commands.put("help", HelpCommand.class);
        //System.out.println(Command.class);
        //System.out.println(commandClasses);
        //for(var command : commandClasses){
        //    if(command.isAnnotationPresent(CommandAnnotation.class)){
        //        var annotation = command.getDeclaredAnnotation(CommandAnnotation.class);
        //        try {
        //            System.out.println(annotation.name());
        //            commands.put(annotation.name(), command.getConstructor(HelperBot.class));
        //        } catch (NoSuchMethodException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //}
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
