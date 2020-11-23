package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CommandName(name = "repinf", info = "show info about concrete repository")
public class RepositoryInfoCommand extends Command {

    @Parameter(description = "[userName] [repositoryName]")
    private List<String> arguments;

    public RepositoryInfoCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(arguments == null || arguments.size() != 2)
            throw new IllegalArgumentException("$repinf [userName] [repositoryName]");

        var service = new RepositoryService();
        var result = new StringBuilder();
        try {
            var repo = service.getRepository(arguments.get(0), arguments.get(1));
            result.append("name: ").append(repo.getName()).append("\n");
            result.append("created at: ").append(repo.getCreatedAt().toString()).append("\n");
            result.append("forks: ").append(repo.getForks()).append("\n");
            if(repo.getDescription() != null && !repo.getDescription().isEmpty())
                result.append("description: ").append(repo.getDescription()).append("\n");
            result.append("languages: ").append(repo.getLanguage()).append("\n");
            result.append("size: ").append(repo.getSize()).append("\n");
            result.append("last commit at: ").append(getLastCommitDate(repo)).append("\n");
            result.append("contributors: ").append(getContributors(service.getContributors(repo, true)));

            sender.send(result.toString());
        } catch (IOException e) {
            throw new IllegalArgumentException("something going wrong at \"RepositoryInfoCommand\"");
        }
    }

    private String getContributors(List<Contributor> contributors){
        return contributors.stream().map(Contributor::getLogin).collect(Collectors.joining(", "));
    }

    private Date getLastCommitDate(IRepositoryIdProvider repository) throws IOException {
        return new CommitService().getCommits(repository).get(0).getCommit().getCommitter().getDate();
    }
}
