package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

@CommandName(name = "allrepos", info = "Show user repositories")
public class RepositoriesCommand extends Command {

    @Parameter(description = "[userNick]")
    private List<String> userName;

    public RepositoriesCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(userName == null || userName.size() != 1)
            throw new IllegalArgumentException("enter user name");

        var count = 1;
        try {
            for (var repo : new RepositoryService().getRepositories(userName.get(0)))
                sender.send(MessageFormat.format("{0}) {1} - created on {2}", count++,
                        repo.getName(), repo.getCreatedAt()));
        } catch (IOException e) {
            sender.send("No such github user");
        }
    }
}
