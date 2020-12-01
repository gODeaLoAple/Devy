package main.java.com.urfu.Devy.command.commands.groups;

import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

@CommandName(
        name="chats",
        info="Get ids of chats from every platform"
)
public class GetChatsCommand extends Command {

    public GetChatsCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var sb = new StringBuilder();
        var chats = groupInfo.asChats().getChats();
        if (chats.hasTelegram())
            sb.append("Telegram: %d".formatted(chats.getTelegramId())).append(System.lineSeparator());
        if (chats.hasDiscord())
            sb.append("Discord: %s".formatted(chats.getDiscordId())).append(System.lineSeparator());
        if (sb.length() == 0)
            sb.append("Hmm... Strangely, you has no chats.");
        sender.send(sb.toString().stripTrailing());
    }
}
