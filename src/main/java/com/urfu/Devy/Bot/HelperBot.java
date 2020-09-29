package main.java.com.urfu.Devy.Bot;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Goups.GroupInfo;
import java.lang.reflect.InvocationTargetException;

public class HelperBot {

    private final GroupInfo group;

    public HelperBot(GroupInfo group) {
        this.group = group;
    }

    public String execute(CommandData data) {
        try {
            var command = DiscordBot.getInstance().getCommand(data.getName())
                    .getDeclaredConstructor(HelperBot.class).newInstance(this);
            return command.execute(data);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return "";
        }
    }
}

