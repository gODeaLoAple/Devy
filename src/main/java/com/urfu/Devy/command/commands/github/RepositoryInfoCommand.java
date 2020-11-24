package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.github.GitHubController;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

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

        try {
            sender.send(GitHubController
                    .getRepositoryInfo(new RepositoryService()
                            .getRepository(arguments.get(0), arguments.get(1))));
        } catch (IOException e) {
            sender.send("something went wrong at \"RepositoryInfoCommand\"");
        }
    }
}
