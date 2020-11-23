package main.java.com.urfu.Devy.command.commands.groups;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandName(
        name="merge",
        info="Merge groups from different platforms into one"
)
public class MergeGroupCommand extends GroupCommand {

    @Parameter(names="-d", description = "Merge discord group")
    public boolean discord;

    @Parameter(names="-t", description = "Merge telegram group")
    public boolean telegram;

    @Parameter(description = "[groupId] [password]")
    public List<String> text;

    public MergeGroupCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var group = getGroup();
            var id = text.get(0);
            validatePassword(text.get(1));
            if (telegram)
                group.setTelegram(Long.valueOf(id));
            if (discord)
                group.setDiscord(id);
            RepositoryController.getGroupRepository().updateGroup(group);
            sender.send("Groups merged!");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }

    }

    @Override
    protected void validate() throws CommandException {
        super.validate();
        if (text.size() != 2)
            throw new CommandException("Incorrect count of arguments.");
        if (!telegram && !discord)
            throw new CommandException("Use one of platforms.");
        if (telegram && discord)
            throw new CommandException("Use only one platform.");
    }

    protected void validatePassword(String password) throws CommandException {
        var originalPassword = getGroup().getPassword();
        if (originalPassword != null && !originalPassword.equals(password))
            throw new CommandException("Access denied. Wrong password.");
    }
}
