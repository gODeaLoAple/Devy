package main.java.com.urfu.Devy.command.commands.groups;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

@CommandName(
        name="chats",
        info="Get ids of chats from every platform"
)
public class GetChatsCommand extends GroupCommand {

    public GetChatsCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var group = getGroup();
            if (group.getTelegramId() != null)
                sender.send("Telegram: " + group.getTelegramId());
            if (group.getDiscordId() != null)
                sender.send("Discord: " + group.getDiscordId());
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
