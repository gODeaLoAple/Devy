package main.java.com.urfu.Devy.bot;

import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.groups.GroupInfo;

public class HelperBot {

    protected final GroupInfo group;

    public HelperBot(GroupInfo group) {
        this.group = group;
    }

    public String execute(CommandData data) {
        var command = CommandsController.CreateCommand(data.getName(), this);
        if(command == null)
            return "no such command"; // в будущем заменить на result
        return command.execute(data);
    }
}

