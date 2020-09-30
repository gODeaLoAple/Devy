package main.java.com.urfu.Devy.Bot.Discord;

import main.java.com.urfu.Devy.Bot.Bot;
import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Parser.CommandParser;
import main.java.com.urfu.Devy.Command.Parser.ParseCommandException;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import java.util.HashMap;
import java.util.Map;

public class DiscordBot implements Bot {
    protected final Map<Integer, HelperBot> helpers = new HashMap<>();
    protected final CommandParser parser;
    protected final String token;

    public DiscordBot(String discordToken) {
        token = discordToken;
        parser = new CommandParser("$");
    }

    public void execute(GroupInfo group, String line) {
        var groupId = group.getId();
        if (!helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа не найдена");
        try {
            send(helpers.get(groupId).execute(parser.parse(line)));
        }
        catch (ParseCommandException e) {
            send(e.getMessage());
        }
    }

    @Override
    public void send(String message) {
        System.out.println(message);
    }

    @Override
    public void receive(GroupInfo from, String message) {
        execute(from, message);
    }

    @Override
    public void removeGroup(GroupInfo group) {
       var groupId = group.getId();
       if (!helpers.containsKey(groupId))
           throw new IllegalArgumentException("Группа не была добавлена");
       helpers.remove(groupId);
    }

    @Override
    public void addGroup(GroupInfo group) {
        var groupId = group.getId();
        if (helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа уже была добавлена");
        helpers.put(groupId, new HelperBot(group));
    }

}
