package main.java.com.urfu.Devy.Bot;

import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Command.CommandFactory;
import main.java.com.urfu.Devy.Goups.GroupInfo;

public class HelperBot {

    protected final GroupInfo group;

    public HelperBot(GroupInfo groupInfo) {
        group = groupInfo;
    }

    public String execute(CommandData data) {
        return CommandFactory.create(this, data.getName()).execute(data);
    }
}

