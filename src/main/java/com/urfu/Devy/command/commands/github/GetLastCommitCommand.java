package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.dbunit.DatabaseUnitException;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@CommandName(name = "lastcommit", info = "Last commit from saved repository.")
public class GetLastCommitCommand extends Command{

    public GetLastCommitCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {

        try {
            validate();
            var service = new RepositoryService();
            var data = GitHubController.getRepositoryInfoFromDataBase(groupInfo.getId());
            var repo = service.getRepository(data.getName(), data.getRepositoryName());
            sender.send(GitHubController.getCommitInfo(GitHubController.getLastCommit(repo)));
        }
        catch (CommandException e) {
            sender.send(e.getMessage());
        } catch (IOException | DatabaseUnitException e) {
            sender.send("Something went wrong. Try again.");
            e.printStackTrace();
        }
    }

    protected void validate() throws CommandException {
        if(!groupInfo.asGithub().hasRepository())
            throw new HasNotRepositoryException();
    }
}

class HasNotRepositoryException extends CommandException {

    private static final String addReposName = CommandsController.getCommandName(AddRepositoryCommand.class);
    private static final String errMessage = createMessage();

    public HasNotRepositoryException() {
        super(errMessage);
    }

    private static String createMessage() {
        return "Your group doesn't have a repository."
             + System.lineSeparator()
             + "Firstly add it by \"%s\" command".formatted(addReposName);
    }

}
