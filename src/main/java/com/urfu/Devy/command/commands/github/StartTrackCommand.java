package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

@CommandName(name = "track", info = "Start track your repository.")
public class StartTrackCommand extends Command {

    public StartTrackCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            GitHubController.startTrackRepository(groupInfo, sender);
            sender.send("Tracking started.");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    protected void validate() throws CommandException {
        if(!groupInfo.asGithub().hasRepository())
            throw new HasNotRepositoryException();
    }
}
