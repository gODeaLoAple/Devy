package main.java.com.urfu.Devy.command.commands.common;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;

import java.util.List;

public class UnknownCommand extends Command {

    @Parameter
    public List<String> text;

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
