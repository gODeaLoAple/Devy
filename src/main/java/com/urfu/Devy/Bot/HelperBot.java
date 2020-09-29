package main.java.com.urfu.Devy.Bot;

import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Command.CommandsController;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import java.lang.reflect.InvocationTargetException;

public class HelperBot {

    protected final GroupInfo group;

    public HelperBot(GroupInfo groupInfo) {
        group = groupInfo;
    }

    public String execute(CommandData data) {
        try {
            var command = CommandsController.getCommand(data.getName())
                    .getDeclaredConstructor(HelperBot.class).newInstance(this);
            return command.execute(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }
}

