package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.BotBuildException;
import main.java.com.urfu.Devy.bot.BotBuilder;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import java.util.NoSuchElementException;

public class DiscordBot extends Bot {
    private final String token;

    public DiscordBot(String discordToken) {
        super("$");
        token = discordToken;
    }

    @Override
    public void start(BotBuilder builder) {
        log.info("Start bot...");
        try {
            builder.build(this, token);
            log.info("Bot started successfully!");
        } catch (BotBuildException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    public void handleMessage(String guildId, MessageSender sender, String message) throws ParseCommandException {
        handleMessage(getGroupOrCreate(guildId), sender, message);
    }

    public GroupInfo getGroupOrCreate(String guildId) {
        try {
            var groupId = RepositoryController
                    .getChatsRepository()
                    .getGroupChatsByDiscordChatId(guildId)
                    .getGroupId();
            return RepositoryController
                    .getGroupRepository()
                    .getGroupById(groupId);
        } catch (NoSuchElementException e) {
            var group = new GroupInfo();
            group.asChats().setDiscord(guildId);
            addGroup(group);
            return group;
        }
    }

    //public void startTrackingOnLoad(){
    //    var trackingGroupsData = RepositoryController
    //            .getGitHubRepository()
    //            .getAllTrackingGroups();
    //    for (var groupData : trackingGroupsData){
    //        var group = this.groups.get(groupData.getGroupId());
    //        GitHubController.startTrackRepository(group, group.getSender(groupData.getChatId()));
    //    }
    //}
}