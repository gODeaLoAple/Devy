package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

@CommandName(name = "stoptrack", info = "stop track repository")
public class StopTrackCommand extends Command {

    public StopTrackCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute(){
        GitHubController.stopTrackRepository(groupInfo);
        sender.send("tracking removed");
    }
}
