package main.java.com.urfu.Devy.Bot;

import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Command.CommandFactory;
import main.java.com.urfu.Devy.Goups.GroupInfo;

public class HelperBot {

    private final GroupInfo group;

    public HelperBot(GroupInfo group) {
        this.group = group;
    }

    public String execute(CommandData data) {
        return CommandFactory.create(this, data.getName()).execute(data);
    }
}

