package main.java.com.urfu.Devy.command.commands;

import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;

public class UnknownCommand extends Command {
    public UnknownCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        sender.send("Unknown command. Try again!");
    }

    @Override
    public boolean isNull() { return true; }

}
