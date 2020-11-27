package main.java.com.urfu.Devy.bot;


import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;

public abstract class Bot {
    private final CommandParser parser;
    protected final Logger log;

    public Bot(String commandPrefix) {
        parser = new CommandParser(commandPrefix);
        log = Logger.getLogger(getClass().getSimpleName());
    }

    public void removeGroup(GroupInfo group) {
        if (!hasGroup(group))
            throw new IllegalArgumentException("The group was not found");
        RepositoryController.getGroupRepository().removeGroup(group);
        log.info("Group was removed: " + group.getId());
    }

    public void addGroup(GroupInfo group) {
        if (hasGroup(group))
            throw new IllegalArgumentException("The group have been added already");
        RepositoryController.getGroupRepository().addGroup(group);
        log.info("Group was added: " + group.getId());
    }

    public boolean hasGroup(GroupInfo group) {
        return RepositoryController.getGroupRepository().hasGroup(group.getId());
    }

    public void handleMessage(GroupInfo group, MessageSender sender, String message)
            throws ParseCommandException {
        group.execute(parser.parse(message), sender);
    }

    public boolean isCommand(String message) {
        return parser.isCommand(message);
    }

    public abstract void start();

}