package Bot;

import Command.CommandData;
import Command.CommandFactory;

public class DiscordHelperBot implements HelperBot{

    private final GroupInfo group;

    public DiscordHelperBot(GroupInfo group) {
        this.group = group;
    }

    public void execute(CommandData data) {
        CommandFactory.create(this, data.getName()).execute(data);
    }

    public void send(String message){
        DiscordBot.getInstance().Send(message);
    }
}
