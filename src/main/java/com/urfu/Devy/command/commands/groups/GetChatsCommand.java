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
            var sb = new StringBuilder();
            if (group.getTelegramId() != null)
                sb.append("Telegram: %d".formatted(group.getTelegramId())).append(System.lineSeparator());
            if (group.getDiscordId() != null)
                sb.append("Discord: %s".formatted(group.getDiscordId())).append(System.lineSeparator());
            if (sb.length() == 0)
                sb.append("Hmm... Strangely, you has no chats.");
            sender.send(sb.toString().stripTrailing());
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
