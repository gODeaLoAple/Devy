package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.CommandArgumentsCountException;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.formatter.TextFormatter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@CommandName(name = "commits", info="Show last commits of person")
public class RepositoryCommitsCommand extends Command {

    private static final int maxCommits = 25;
    private static final int defaultCommits = 9;

    @Parameter(names = "-n", description = "Commits count.\n\t  Max: " + maxCommits + "\n\t Default: " + defaultCommits)
    protected int commitsToShow;

    @Parameter(description = "[userName] [repositoryName]")
    private List<String> arguments;

    public RepositoryCommitsCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {

        try {
            validate();
            final var repo = new RepositoryId(arguments.get(0), arguments.get(1));
            final var service = new CommitService();
            for (var commit : getFirstNElements(service.getCommits(repo), commitsToShow)) {
                sender.send(getCommitInfo(commit.getCommit())
                        + "================================="
                        + System.lineSeparator());
            }
        }
        catch (Exception e){
            sender.send("Something went wrong. Try again.");
            e.printStackTrace();
        }
    }

    protected void validate() throws CommandException {
        if (arguments == null || arguments.size() != 2)
            throw new CommandArgumentsCountException(2);
        if (commitsToShow > maxCommits)
            throw new CommandException("Max commits number: " + maxCommits);
        if (commitsToShow == 0)
            commitsToShow = defaultCommits;
        if (commitsToShow < 0)
            throw new CommandException("Expected positive number of commits.");
    }

    private List<RepositoryCommit> getFirstNElements(List<RepositoryCommit> list, int count){
        return list.stream().limit(count)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getCommitInfo(Commit commit) {
        return sender
                .createFormatter()
                .bold("Author: ").raw(commit.getAuthor().getName()).line()
                .bold("Message: ").line().code(commit.getMessage()).line()
                .bold("Date: " ).raw(commit.getCommitter().getDate().toString()).line()
                .get();
    }
}
