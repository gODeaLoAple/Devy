package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

@CommandName(name = "removerep", info = "remove data about your repository")
public class RemoveRepositoryCommand extends Command {

    public RemoveRepositoryCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        if(!group.hasRepository())
            throw new IllegalArgumentException("Your group doesn't have a repository.\nFirstly add it by \"addrepos\" command");
        try {
            group.removeRepository();
            sender.send("repository removed");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
