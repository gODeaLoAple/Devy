package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandArgumentsCountException;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CommandName(name = "repinf", info = "Show info about concrete repository.")
public class RepositoryInfoCommand extends Command {

    @Parameter(description = "[userName] [repositoryName]")
    private List<String> arguments;

    public RepositoryInfoCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {

        try {
            validate();
            var repository = new RepositoryService().getRepository(arguments.get(0), arguments.get(1));
            sender.send(formatRepositoryInfo(repository));
        } catch (CommandException e) {
            sender.send(e.getMessage());
        } catch (IOException e) {
            sender.send("Something went wrong. Try again.");
            e.printStackTrace();
        }
    }

    protected void validate() throws CommandException {
        if(arguments == null || arguments.size() != 2)
            throw new CommandArgumentsCountException(2);
    }

    private String formatRepositoryInfo(Repository repository) {
        var formatter = sender.createFormatter();
        formatter.bold("Name: ").raw(repository.getName()).line();
        formatter.bold("Created at: ").raw(repository.getCreatedAt().toString()).line();
        formatter.bold("Forks").raw(Integer.toString(repository.getForks())).line();
        if (repository.getDescription() != null && !repository.getDescription().isEmpty())
            formatter.bold("Description: ").raw(repository.getDescription()).line();
        formatter.bold("Languages: ").raw(repository.getLanguage()).line();
        formatter.bold("Size: ").raw(repository.getSize() + "MB").line();
        try {
            formatter.bold("Last commit at: ").raw(GitHubController.getLastCommitDate(repository).toString()).line();
            formatter.bold("Contributors: ").raw(getContributors(new RepositoryService().getContributors(repository, true))).line();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formatter.get();
    }

    private static String getContributors(List<Contributor> contributors){
        return contributors.stream().map(Contributor::getLogin).filter(Objects::nonNull).collect(Collectors.joining(", "));
    }
}
