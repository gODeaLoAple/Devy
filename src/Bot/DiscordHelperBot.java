package Bot;

import Command.CommandData;
import Command.CommandFactory;

public class DiscordHelperBot {

    private final GroupInfo group;

    public DiscordHelperBot(GroupInfo group) {
        this.group = group;
    }

    public void Execute(CommandData data) {
        DiscordBot.getInstance()
                .Send(CommandFactory.Create(group, data).Execute().Stringify());
    }
}
