package main.java.com.urfu.Devy.github;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;

import java.sql.Time;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GithubRepositoryWatcher {

    private final long startDelay = 1_000L;
    private final long period = Duration.ofHours(1).toMillis();
    private final Map<Integer, Timer> timers;

    public GithubRepositoryWatcher() {
        timers = new HashMap<>();
    }

    public void setTracking(GroupInfo group, TimerTask repeatedTask) {
        var groupId = group.getId();
        var timer = new Timer("Timer");
        timer.scheduleAtFixedRate(repeatedTask, startDelay, period);
        timers.put(groupId, timer);
        RepositoryController.getGitHubRepository().setTracking(groupId, true);

    }

    public void unsetTracking(GroupInfo group) {
        var groupId = group.getId();
        if (!timers.containsKey(groupId))
            return;
        timers.get(groupId).cancel();
        timers.remove(groupId);
        RepositoryController.getGitHubRepository().setTracking(groupId, false);
    }
}
