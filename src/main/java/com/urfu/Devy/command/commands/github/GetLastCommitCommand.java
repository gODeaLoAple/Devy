package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.github.GitHubController;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@CommandName(name = "lastcommit", info = "last commit from saved repository")
public class GetLastCommitCommand extends Command{

    public GetLastCommitCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(!group.hasRepository())
            throw new IllegalArgumentException("Your group doesn't have a repository.\nFirstly add it by \"addrepos\" command");
        var service = new RepositoryService();
        try {
            var data = group.getRepository();
            var repo = service.getRepository(data.getName(), data.getRepositoryName());
            sender.send(GitHubController.getCommitInfo(GitHubController.getLastCommit(repo)));

        } catch (IOException e) {
            throw new IllegalArgumentException("something went wrong at \"GetLastCommitCommand\"");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
