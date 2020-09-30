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
        var command = CommandsController.getCommandInstance(data.getName(), this);
        if(command == null)
            return "no such command"; // в будущем заменить на result
        return command.execute(data);
    }
}

