package main.java.com.urfu.Devy.bot;


import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;

public abstract class Bot {
    public static final String UserWarningMessage = """
                    Hello, I'm Devy - bot for development. 
                    I'm sorry, but I work only in text-channels now.
                    You can create your own text-channel and call me there!""";
    private final CommandParser parser;
    protected final Logger log;

    public Bot(String commandPrefix) {
        parser = new CommandParser(commandPrefix);
        log = Logger.getLogger(getClass().getSimpleName());
    }

    protected GroupInfo createGroup() {
        var group = new GroupInfo();
        addGroup(group);
        return group;
    }

    public void addGroup(GroupInfo group) {
        if (hasGroup(group))
            throw new IllegalArgumentException("The group has been added already");
        RepositoryController.getGroupRepository().addGroup(group);
        log.info("Group was added: " + group.getId());
    }

    public boolean hasGroup(GroupInfo group) {
        return RepositoryController.getGroupRepository().hasGroup(group.getId());
    }

    public void handleMessage(GroupInfo group, MessageSender sender, String message) {
        try {
            group.execute(parser.parse(message), sender);
        }
        catch (IllegalArgumentException | ParseCommandException e){
            sendMessage(sender, e.getMessage());
        } catch (Exception e){
            log.error("On 'handleMessage': " + e.getMessage());
        }
    }

    public void sendMessage(MessageSender sender, String message) {
        sender.send(message);
    }

    public boolean isCommand(String message) {
        return parser.isCommand(message);
    }

    public void startTrackRepositoriesOnStart(){
        var trackingGroups = RepositoryController.getGitHubRepository().getAllTrackingGroups();
        for (var groupData : trackingGroups) {
            var sender = getSenderById(groupData.getChatId());
            if(sender == null)
                continue;
            GitHubController.startTrackRepository(groupData.getGroup(), sender);
        }
    }

    public abstract void start(BotBuilder builder);

    public abstract String getToken();

    public abstract MessageSender getSenderById(String id);
}