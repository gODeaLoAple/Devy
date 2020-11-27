package main.java.com.urfu.Devy.bot.discord;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.NoSuchElementException;
import main.java.com.urfu.Devy.github.GitHubController;

public class DiscordBot extends Bot {
    private final String token;

    public DiscordBot(String discordToken) {
        super("$");
        token = discordToken;
    }

    @Override
    public void start() {
        log.info("Start bot...");
        try {
            var jda = JDABuilder
                    .createDefault(token)
                    .addEventListeners(new DiscordBotListener(this))
                    .build()
                    .awaitReady();
            //startTrackingOnLoad();
            log.info("Bot started successfully!");
        } catch (InterruptedException | LoginException e) {
            log.error("Error on start: " + e.getMessage());
        }
    }

    public void handleMessage(String guildId, MessageSender sender, String message) throws ParseCommandException {
        handleMessage(getGroupOrCreate(guildId), sender, message);
    }

    private GroupInfo getGroupOrCreate(String guildId) {
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

    public void onGuildReady(String guildId) {
        getGroupOrCreate(guildId);
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