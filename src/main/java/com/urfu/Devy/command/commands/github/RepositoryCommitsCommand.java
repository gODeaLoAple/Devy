package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;

@CommandName(name = "commits",
        info="Show last commits of person",
        detailedInfo = "$commits [userName] [repositoryName] [-n]")
public class RepositoryCommitsCommand extends Command {

    private final int maxCommits = 25;
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
        if(commitsToShow > maxCommits)
            throw new IllegalArgumentException("max commits number: %d".formatted(maxCommits));
        if(commitsToShow == 0)
            commitsToShow = 10;

        final var repo = new RepositoryId(arguments.get(0), arguments.get(1));
        final var service = new CommitService();
        final var sb = new StringBuilder();
        for (var commits : service.pageCommits(repo, commitsToShow)) {
            for (var commit : commits) {
                var commitData = commit.getCommit();
                var text = commitData.getMessage();
                var author = commitData.getAuthor().getName();
                var date = commitData.getAuthor().getDate();
                var message = "**\"{0}\"** by *{1}* on *{2}*";
                sb.append(MessageFormat.format(message, text, author,
                        date)).append(System.lineSeparator());
            }
        }
        sender.send(sb.toString());
    }
}
