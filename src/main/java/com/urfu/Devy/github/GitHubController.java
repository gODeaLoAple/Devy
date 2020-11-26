package main.java.com.urfu.Devy.github;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GitHubController {
    private static final Logger log = Logger.getLogger(GitHubController.class);

    public static IRepositoryIdProvider getRepository(RepositoryInfo repos) throws IOException{
        return new RepositoryService().getRepository(repos.getName(), repos.getRepositoryName());
    }

    public static RepositoryInfo getRepositoryInfoFromDataBase(String groupId) throws DatabaseUnitException {
        if(RepositoryController.getGitHubRepository().hasRepository(groupId))
            return RepositoryController.getGitHubRepository().getRepository(groupId);
        throw new DatabaseUnitException("no such repository");
    }

    public static Commit getLastCommit(IRepositoryIdProvider repos) throws IOException{
        return new CommitService().getCommits(repos).get(0).getCommit();
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
        Timer timer = new Timer("Timer");
        long delay  = 1_000L;
        long oneHour = 216_000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, oneHour);
        group.startTrack(timer);
    }

    public static void taskToRun(GroupInfo group, MessageSender sender) throws IOException{
        try {
            var info = getRepositoryInfoFromDataBase(group.getId());
            var repos = getRepository(info);
            var lastCommit = getLastCommitDate(repos);
            var groupLastCommit = group.getLastCommitDate();
            if(groupLastCommit == null)
                groupLastCommit = "";
            if(!groupLastCommit.equals(lastCommit.toString())){
                sender.send(getCommitInfo(getLastCommit(repos)));
                RepositoryController.getGitHubRepository().setLastCommitDate(group.getId(), lastCommit.toString());
            }
            else {
                sender.send("no changes");
            }
        } catch (DatabaseUnitException e) {
            log.error("On \"taskToRun\"", e);
        }
    }

    public static String getCommitInfo(Commit commit){
        return "author: " + commit.getCommitter().getName() + "\n" +
                "message: " + commit.getMessage() + "\n" +
                "date: " + commit.getCommitter().getDate();
    }

    public static String getRepositoryInfo(Repository repository) {
        var result = new StringBuilder();
        try {
            result.append(getSeparatedString("name", repository.getName()));
            result.append(getSeparatedString("created at", repository.getCreatedAt().toString()));
            result.append(getSeparatedString("forks", Integer.toString(repository.getForks())));
            if (repository.getDescription() != null && !repository.getDescription().isEmpty())
                result.append(getSeparatedString("description", repository.getDescription()));
            result.append(getSeparatedString("languages", repository.getLanguage()));
            result.append(getSeparatedString("size", Integer.toString(repository.getSize())));
            result.append(getSeparatedString("last commit at", getLastCommitDate(repository).toString()));
            result.append(getSeparatedString("contributors",
                    getContributors(new RepositoryService().getContributors(repository, true))));

            return result.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("something went wrong at \"getRepositoryInfo\"");
        }
    }

    private static String getSeparatedString(String name, String data){
        return "%s: %s\n".formatted(name, data);
    }

    private static String getContributors(List<Contributor> contributors){
        return contributors.stream().map(Contributor::getLogin).filter(Objects::nonNull).collect(Collectors.joining(", "));
    }
}
