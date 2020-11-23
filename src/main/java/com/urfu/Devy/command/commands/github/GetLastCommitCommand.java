package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import org.eclipse.egit.github.core.service.CommitService;
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
        var result = new StringBuilder();
        try {
            var data = group.getRepository();
            var repo = service.getRepository(data.getName(), data.getRepositoryName());
            var commit = new CommitService().getCommits(repo).get(0).getCommit();

            result.append("author: ").append(commit.getCommitter().getName()).append("\n");
            result.append("message: ").append(commit.getMessage()).append("\n");
            result.append("sha: ").append(commit.getSha()).append("\n");
            result.append("date: ").append(commit.getCommitter().getDate());
            sender.send(result.toString());

        } catch (IOException e) {
            throw new IllegalArgumentException("something going wrong at \"RepositoryInfoCommand\"");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
