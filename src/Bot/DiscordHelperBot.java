package Bot;

import Command.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DiscordHelperBot implements HelperBot{

    private final GroupInfo group;
    public DiscordHelperBot(GroupInfo group) {
        this.group = group;
    }

    public void execute(CommandData data) {
        try {
            var command = (Command) DiscordBot.getInstance().getCommand(data.getName()).newInstance(this);
            command.execute(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        DiscordBot.getInstance().send(message);
    }
}
