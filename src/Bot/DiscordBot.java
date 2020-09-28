package Bot;

import Command.Command;
import Command.CommandAnnotation;
import au.org.consumerdatastandards.reflection.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.reflections.*;

public class DiscordBot implements Bot {
    private static final String DISCORD_BOT_TOKEN = "";
    private static DiscordBot instance;
    private final Map<Integer, DiscordHelperBot> helpers = new HashMap<>();
    private final Map<String, Constructor> commands = new HashMap<>();
    private final DiscordCommandParser parser;

    private DiscordBot() {
        parser = new DiscordCommandParser("$");
        constructCommandsDictionary();

    }

    private void constructCommandsDictionary(){
        var commandClasses = (new Reflections(Command.class)).getSubTypesOf(Command.class);
        for(var command : commandClasses){
            if(command.isAnnotationPresent(CommandAnnotation.class)){
                var annotation = command.getDeclaredAnnotation(CommandAnnotation.class);
                try {
                    commands.put(annotation.name(), command.getConstructor(DiscordHelperBot.class));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Constructor getCommand(String commandName){
        if(!commands.containsKey(commandName))
            throw new IllegalArgumentException("no such command yet");
        return commands.get(commandName);
    }

    public static DiscordBot getInstance() {
        if (instance == null)
            instance = new DiscordBot();
        return instance;
    }

    public void execute(GroupInfo group, String line){
        var groupId = group.getId();
        if (!helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа не найдена");
        helpers.get(groupId).execute(parser.parse(line));
    }

    @Override
    public void send(String message) {

    }

    @Override
    public void receive(GroupInfo from, String message){
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
        helpers.put(groupId, new DiscordHelperBot(group));
    }
}

