package main.java.com.urfu.Devy.github;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GitHubController {
    private static final Logger log = Logger.getLogger(GitHubController.class);

    public static IRepositoryIdProvider getRepository(RepositoryInfo repos) throws IOException{
        return new RepositoryService().getRepository(repos.getName(), repos.getRepositoryName());
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
        long period = 20_000L;//216_000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        group.startTrack(timer);
    }

    public static void taskToRun(GroupInfo group, MessageSender sender) throws IOException{
        try {
            var info = group.getRepository();
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
        } catch (CommandException e) {
            log.error("On \"taskToRun\"", e);
        }
    }

    public static String getCommitInfo(Commit commit){
        return "author: " + commit.getCommitter().getName() + "\n" +
                "message: " + commit.getMessage() + "\n" +
                "sha: " + commit.getSha() + "\n" +
                "date: " + commit.getCommitter().getDate();
    }

    public static String getRepositoryInfo(Repository repository) {
        var result = new StringBuilder();
        try {
            result.append("name: ").append(repository.getName()).append("\n");
            result.append("created at: ").append(repository.getCreatedAt().toString()).append("\n");
            result.append("forks: ").append(repository.getForks()).append("\n");
            if (repository.getDescription() != null && !repository.getDescription().isEmpty())
                result.append("description: ").append(repository.getDescription()).append("\n");
            result.append("languages: ").append(repository.getLanguage()).append("\n");
            result.append("size: ").append(repository.getSize()).append("\n");
            result.append("last commit at: ").append(GitHubController.getLastCommitDate(repository)).append("\n");
            result.append("contributors: ").append(getContributors(new RepositoryService().getContributors(repository, true)));

            return result.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("something going wrong at \"getRepositoryInfo\"");
        }
    }

    private static String getContributors(List<Contributor> contributors){
        return contributors.stream().map(Contributor::getLogin).collect(Collectors.joining(", "));
    }

}
