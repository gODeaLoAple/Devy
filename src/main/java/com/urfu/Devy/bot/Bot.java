package main.java.com.urfu.Devy.bot;


import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class Bot {
    private final Map<String, GroupInfo> groups;
    private final CommandParser parser;
    protected final Logger log;

    public Bot(String commandPrefix) {
        groups = new HashMap<>();
        parser = new CommandParser(commandPrefix);
        log = Logger.getLogger(getClass().getSimpleName());
    }

    public void removeGroup(GroupInfo group) {
        if (!groups.containsKey(group.getId()))
            throw new IllegalArgumentException("The group had not been added.");
        groups.remove(group.getId());
    }

    public void addGroup(GroupInfo group) {
        if (groups.containsKey(group.getId()))
            throw new IllegalArgumentException("The group had been added.");
        groups.put(group.getId(), group);
        log.info("Group was added: " + group.getId());
    }

    public void handleMessage(String groupId, MessageSender sender, String message)
            throws ParseCommandException {
        if(!groups.containsKey(groupId))
            groups.put(groupId, new GroupInfo(groupId));
        groups.get(groupId).execute(parser.parse(message), sender);
    }

    public boolean isCommand(String message) {
        return parser.isCommand(message);
    }

    public abstract void start();

}