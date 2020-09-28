package Bot;

import Command.CommandData;
import Command.CommandFactory;
import Goups.GroupInfo;

public class HelperBot {

    private final GroupInfo group;

    public HelperBot(GroupInfo group) {
        this.group = group;
    }

    public String execute(CommandData data) {
        return CommandFactory.create(this, data.getName()).execute(data);
    }
}

