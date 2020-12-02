package main.java.com.urfu.Devy.bot;


import main.java.com.urfu.Devy.command.parser.CommandParser;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.github.GitHubController;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;

public abstract class Bot {
    private final CommandParser parser;
    protected final Logger log;

    public Bot(String commandPrefix) {
        parser = new CommandParser(commandPrefix);
        log = Logger.getLogger(getClass().getSimpleName());
    }

    public void removeGroup(GroupInfo group) {
        if (!hasGroup(group))
            throw new IllegalArgumentException("The group was not found");
        RepositoryController.getGroupRepository().removeGroup(group);
        RepositoryController.getGitHubRepository().removeRepository(group.getId());
        RepositoryController.getTodoRepository().removeAllTodo(group.getId());
        RepositoryController.getChatsRepository().removeChats(group.asChats());
        log.info("Group was removed: " + group.getId());
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
            GitHubController.startTrackRepository(groupData.getGroup(), getSenderById(groupData.getChatId()));
        }
    }

    public abstract void start(BotBuilder builder);

    public abstract String getToken();

    public abstract MessageSender getSenderById(String id);
}