package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.dbunit.DatabaseUnitException;
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
        if(!groupInfo.asGithub().hasRepository())
            throw new IllegalArgumentException("Your group doesn't have a repository.\nFirstly add it by \"addrepos\" command");
        var service = new RepositoryService();
        try {
            var data = GitHubController.getRepositoryInfoFromDataBase(groupInfo.getId());
            var repo = service.getRepository(data.getName(), data.getRepositoryName());
            sender.send(GitHubController.getCommitInfo(GitHubController.getLastCommit(repo)));

        } catch (IOException e) {
            throw new IllegalArgumentException("something went wrong at \"GetLastCommitCommand\"");
        } catch (DatabaseUnitException e) {
            sender.send(e.getMessage());
        }
    }
}
