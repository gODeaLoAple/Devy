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

    public void Execute(GroupInfo group, String line) {
        var groupId = group.getId();
        if (!helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа не найдена");
        helpers.get(groupId).execute(parser.Parse(line));
    }

    @Override
    public void Send(String message) {

    }

    @Override
    public void Receive(GroupInfo from, String message) {
        Execute(from, message);
    }

    @Override
    public void RemoveGroup(GroupInfo group) {
       var groupId = group.getId();
       if (!helpers.containsKey(groupId))
           throw new IllegalArgumentException("Группа не была добавлена");
       helpers.remove(groupId);
    }

    @Override
    public void AddGroup(GroupInfo group) {
        var groupId = group.getId();
        if (helpers.containsKey(groupId))
            throw new IllegalArgumentException("Группа уже была добавлена");
        helpers.put(groupId, new DiscordHelperBot(group));
    }

}

