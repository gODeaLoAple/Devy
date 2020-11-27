package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandName(name = "addrepos", info = "save data about your repository to database")
public class AddRepositoryCommand extends Command {

    @Parameter(description = "[userName] [repositoryName]")
    private List<String> arguments;

    public AddRepositoryCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(arguments == null || arguments.size() != 2)
            throw new IllegalArgumentException("$addrepos [userName] [repositoryName]");
        var groupId = groupInfo.getId();
        if(RepositoryController.getGitHubRepository().hasRepository(groupId))
            throw new IllegalArgumentException("Your group already have repository.\nFirstly remove it by \"removerep\" command");
        try {
            groupInfo.asGithub().addRepository(arguments.get(0), arguments.get(1));
            sender.send("Repository added");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
