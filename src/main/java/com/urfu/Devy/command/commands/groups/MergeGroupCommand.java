package main.java.com.urfu.Devy.command.commands.groups;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.Command;
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
public class MergeGroupCommand extends Command {

    @Parameter(names="-d", description = "Merge discord group")
    public boolean discord;

    @Parameter(names="-t", description = "Merge telegram group")
    public boolean telegram;

    @Parameter(description = "[groupId]")
    public List<String> text;

    public MergeGroupCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var id = text.get(0);
            var chats = groupInfo.asChats().getChats();
            if (telegram)
                chats.setTelegramId(Long.valueOf(id));
            if (discord)
                chats.setDiscordId(id);
            sender.send("Groups merged!");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }

    }

    protected void validate() throws CommandException {
        if (text == null || text.size() < 1)
            throw new CommandException("Incorrect count of arguments.");
        if (!telegram && !discord)
            throw new CommandException("Option not found.");
        if (telegram && discord)
            throw new CommandException("Use only one platform.");
        if (telegram && !isLong(text.get(0)))
            throw new CommandException("Incorrect chat id.");
        var chats = groupInfo.asChats().getChats();
        if (telegram && chats.hasTelegram()
            || discord && chats.hasDiscord())
            throw new CommandException("You cannot merge on this platform.");
    }

    private boolean isLong(String str) {
        if (str == null)
            return false;
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
