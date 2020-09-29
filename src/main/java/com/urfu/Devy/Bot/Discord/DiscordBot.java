package main.java.com.urfu.Devy.Bot.Discord;

import main.java.com.urfu.Devy.Bot.Bot;
import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Command;
import main.java.com.urfu.Devy.Command.CommandName;
import main.java.com.urfu.Devy.Command.CommandParser;
import main.java.com.urfu.Devy.Command.Commands.HelpCommand;
import main.java.com.urfu.Devy.Command.Commands.PingCommand;
import main.java.com.urfu.Devy.Goups.GroupInfo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscordBot implements Bot {
    public static final String DISCORD_BOT_TOKEN = "";
    protected static DiscordBot instance;
    protected final Map<Integer, HelperBot> helpers = new HashMap<>();
    private final Map<String, Class<? extends Command>> commands = new HashMap<>(){};
    protected final CommandParser parser;

    private DiscordBot() {
        constructCommandsDictionary();
        parser = new CommandParser("$");
    }

    public static DiscordBot getInstance() {
        if (instance == null)
            instance = new DiscordBot();
        return instance;
    }

    private void constructCommandsDictionary(){
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

    public Class<? extends Command> getCommand(String commandName) {
        if (!commands.containsKey(commandName))
            throw new IllegalArgumentException("no such command yet");
        return commands.get(commandName);
    }

    public ArrayList<CommandName> getAllCommands(){
        var result = new ArrayList<CommandName>();
        for(var a : commands.values())
            result.add(a.getDeclaredAnnotation(CommandName.class));
        return result;
    }

    public void execute(GroupInfo group, String line) {
        var groupId = group.getId();
        if (!helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа не найдена");
        send(helpers.get(groupId).execute(parser.parse(line)));
    }

    @Override
    public void send(String message) {
        System.out.println(message);
    }

    @Override
    public void receive(GroupInfo from, String message) {
        execute(from, message);
    }

    @Override
    public void removeGroup(GroupInfo group) {
       var groupId = group.getId();
       if (!helpers.containsKey(groupId))
           throw new IllegalArgumentException("Группа не была добавлена");
       helpers.remove(groupId);
    }

    @Override
    public void addGroup(GroupInfo group) {
        var groupId = group.getId();
        if (helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа уже была добавлена");
        helpers.put(groupId, new HelperBot(group));
    }

}
