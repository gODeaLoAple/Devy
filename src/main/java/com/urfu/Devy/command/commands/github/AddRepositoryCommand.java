package main.java.com.urfu.Devy.command.commands.github;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.*;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandName(name = "addrepos", info = "Save data about your repository to database.")
public class AddRepositoryCommand extends Command {

    @Parameter(description = "[userName] [repositoryName]")
    private List<String> arguments;

    public AddRepositoryCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            groupInfo.asGithub().addRepository(arguments.get(0), arguments.get(1), sender.getId());
            sender.send("The repository has added.");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    protected void validate() throws CommandException{
        if (arguments == null || arguments.size() != 2)
            throw new CommandArgumentsCountException("");
        if (RepositoryController.getGitHubRepository().hasRepository(groupInfo.getId()))
            throw new HasRepositoryException();
    }
}

class HasRepositoryException extends CommandException {

    private static final String removeRepositoryName = CommandsController.getCommandName(RemoveRepositoryCommand.class);
    private static final String errorMessage = createMessage();

    public HasRepositoryException() {
        super(errorMessage);
    }

    private static String createMessage() {
        return "Your group already have repository." +
                System.lineSeparator() +
                "Firstly remove it by \"%s\" command.".formatted(removeRepositoryName);
    }
}