package Bot.Discord;

import Bot.Bot;
import Bot.HelperBot;
import Command.CommandParser;
import Goups.GroupInfo;

import java.util.HashMap;
import java.util.Map;

public class DiscordBot implements Bot {
    public static final String DISCORD_BOT_TOKEN = "";
    protected static DiscordBot instance;
    protected final Map<Integer, HelperBot> helpers = new HashMap<>();
    protected final CommandParser parser;

    private DiscordBot() {
        parser = new CommandParser("$");
    }

    public static DiscordBot getInstance() {
        if (instance == null)
            instance = new DiscordBot();
        return instance;
    }

    public void execute(GroupInfo group, String line) {
        var groupId = group.getId();
        if (!helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа не найдена");
        send(helpers.get(groupId).execute(parser.parse(line)));
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
