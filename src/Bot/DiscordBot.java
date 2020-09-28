package Bot;

import java.util.HashMap;
import java.util.Map;

public class DiscordBot implements Bot {
    private static final String DISCORD_BOT_TOKEN = "";
    private static DiscordBot instance;
    private final Map<Integer, DiscordHelperBot> helpers = new HashMap<>();
    private final DiscordCommandParser parser;

    private DiscordBot() {
        parser = new DiscordCommandParser("$");
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
        helpers.get(groupId).execute(parser.parse(line));
    }

    @Override
    public void send(String message) {

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
        helpers.put(groupId, new DiscordHelperBot(group));
    }

}

