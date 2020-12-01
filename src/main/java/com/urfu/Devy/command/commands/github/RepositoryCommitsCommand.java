package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@CommandName(name = "commits",
        info="Show last commits of person",
        detailedInfo = "$commits [userName] [repositoryName] [-n]")
public class RepositoryCommitsCommand extends Command {

    @Parameter(names = "-n", description = "commits count")
    protected int commitsToShow;

    @Parameter(description = "[userName] [repositoryName] [-n]")
    private List<String> arguments;

    public RepositoryCommitsCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(arguments == null || arguments.size() != 2)
            throw new IllegalArgumentException("$commits [userName] [repositoryName] [-n]commits count(default - 10)");
        var maxCommits = 25;
        if(commitsToShow > maxCommits)
            throw new IllegalArgumentException("max commits number: %d".formatted(maxCommits));
        if(commitsToShow == 0)
            commitsToShow = 10;
        var result = new StringBuilder();
        try {
            final var repo = new RepositoryId(arguments.get(0), arguments.get(1));
            final var service = new CommitService();
            for (var commit : getFirstNElements(service.getCommits(repo), commitsToShow))
                result.append(GitHubController.getCommitInfo(commit.getCommit()))
                        .append("\n=================================\n");
            sender.send(result.toString());
        }
        catch (Exception e){
            sender.send("something went wrong on \"RepositoryCommitsCommand\"");
        }
    }

    private List<RepositoryCommit> getFirstNElements(List<RepositoryCommit> list, int count){
        if(list.size() < count)
            count = list.size();
        return IntStream
                .range(0, count)
                .mapToObj(list::get)
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }
}
