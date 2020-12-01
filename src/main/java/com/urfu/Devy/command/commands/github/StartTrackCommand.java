package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

@CommandName(name = "track", info = "start track your repository")
public class StartTrackCommand extends Command {
    public StartTrackCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(!groupInfo.asGithub().hasRepository())
            throw new IllegalArgumentException("Your group doesn't have a repository.\nFirstly add it by \"addrepos\" command");
        GitHubController.startTrackRepository(groupInfo, sender);
        sender.send("tracking started");
    }
}
