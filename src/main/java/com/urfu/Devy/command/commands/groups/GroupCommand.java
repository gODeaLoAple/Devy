package main.java.com.urfu.Devy.command.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.group.Group;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

public abstract class GroupCommand extends Command {
    public GroupCommand(GroupInfo groupInfo, MessageSender sender, @NotNull String[] args) {
        super(groupInfo, sender, args);
    }

    protected void validate() throws CommandException {
        if (!(groupInfo instanceof Group))
            throw new CommandException("Command not available");
    }

    protected Group getGroup() {
        return (Group)groupInfo;
    }
}
