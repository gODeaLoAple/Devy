package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.CommandArgumentsCountException;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.formatter.TextFormatter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.github.RepositoryInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

@CommandName(name = "allrepos", info = "Show user repositories.")
public class RepositoriesCommand extends Command {

    @Parameter(description = "[userNick]")
    private List<String> userName;

    public RepositoriesCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }


    @Override
    public void execute() {
        var count = 1;
        try {
            validate();
            var sb = new StringBuilder();
            for (var repo : new RepositoryService().getRepositories(userName.get(0)))
                sb.append(formatMessage(count++, repo));
            sender.send(sb.toString());
        } catch (IOException e) {
            sender.send("No such github user.");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    protected void validate() throws CommandException {
        if (userName == null || userName.size() != 1)
            throw new CommandArgumentsCountException("Expected user GitHub nickname.");
    }

    private String formatMessage(int count, Repository repository) {
        return sender
                .createFormatter()
                .raw(count + ") ")
                .bold(repository.getName()).raw(" - created on ").cursive(repository.getCreatedAt().toString())
                .line()
                .get();
    }
}
