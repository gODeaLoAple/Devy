package main.java.com.urfu.Devy.group.modules.github;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GitHubController {
    private static final Logger log = Logger.getLogger(GitHubController.class);
    private static final GithubRepositoryWatcher watcher = new GithubRepositoryWatcher();
    private static String token;

    public static void setGitHubToken(String gitHubToken){
        token = gitHubToken;
    }

    public static IRepositoryIdProvider getRepository(RepositoryInfo repos) throws IOException{
        var service = new RepositoryService();
        service.getClient().setOAuth2Token(token);
        return service.getRepository(repos.getName(), repos.getRepositoryName());
    }

    public static RepositoryInfo getRepositoryInfoFromDataBase(int groupId) throws DatabaseUnitException {
        if(RepositoryController.getGitHubRepository().hasRepository(groupId))
            return RepositoryController.getGitHubRepository().getRepository(groupId);
        throw new DatabaseUnitException("no such repository");
    }

    public static Commit getLastCommit(IRepositoryIdProvider repos) throws IOException{
        var service = new CommitService();
        service.getClient().setOAuth2Token(token);
        return service.getCommits(repos).get(0).getCommit();
    }

    public static Date getLastCommitDate(IRepositoryIdProvider repository) throws IOException {
        return getLastCommit(repository).getCommitter().getDate();
    }

    public static void startTrackRepository(GroupInfo group, MessageSender sender){
        var repeatedTask = new TimerTask() {
            public void run() {
                try {
                    taskToRun(group, sender);
                } catch (IOException e) {
                    log.error("on \"startTrackRepository\"", e);
                }
            }
        };
        watcher.setTracking(group, repeatedTask);
    }

    private static void taskToRun(GroupInfo group, MessageSender sender) throws IOException{
        try {
            var info = getRepositoryInfoFromDataBase(group.getId());
            var repos = getRepository(info);
            var lastCommit = getLastCommitDate(repos);
            var groupLastCommit = group.asGithub().getLastCommitDate();

            if(groupLastCommit == null)
                groupLastCommit = "";
            if(!groupLastCommit.equals(lastCommit.toString())) {
                sender.send(getCommitInfo(getLastCommit(repos)));
                RepositoryController.getGitHubRepository().setLastCommitDate(group.getId(), lastCommit.toString());
            }
            else {
                sender.send("no changes"); // TODO remove at production version
            }
        } catch (DatabaseUnitException e) {
            log.error("On \"taskToRun\"", e);
        }
    }

    public static void stopTrackRepository(GroupInfo group) {
        watcher.unsetTracking(group);
    }

    public static String getCommitInfo(Commit commit){
        return "author: " + commit.getAuthor().getName() + "\n" +
                "message: " + commit.getMessage() + "\n" +
                "date: " + commit.getCommitter().getDate();
    }

}
