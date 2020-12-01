package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.implemented.GitHubRepository;
import main.java.com.urfu.Devy.group.modules.github.RepositoryInfo;
import main.java.com.urfu.Devy.group.GroupInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockGithubRepository extends GitHubRepository {

    private final Map<Integer, RepositoryInfo> repositories = new HashMap<>();
    private final Map<Integer, String> chats = new HashMap<>();
    private final Map<Integer, String> commits = new HashMap<>();

    public boolean addRepository(int groupId, String chatId, RepositoryInfo repository) {
        if (repositories.containsKey(groupId))
            return false;
        repositories.put(groupId, repository);
        chats.put(groupId, chatId);
        return true;
    }

    public boolean removeRepository(int groupId) {
        if (!repositories.containsKey(groupId))
            return false;
        repositories.remove(groupId);
        chats.remove(groupId);
        return true;
    }

    public RepositoryInfo getRepository(int groupId) {
        return repositories.get(groupId);
    }

    public Collection<RepositoryInfo> getAllRepositories() {
        return repositories.values();
    }

    public boolean hasRepository(int groupId) {
        return repositories.containsKey(groupId);
    }

    public String getLastCommitDate(int groupId){
        return commits.get(groupId);
    }

    public boolean setLastCommitDate(int groupId, String date){
        commits.put(groupId, date);
        return false;
    }

    public boolean setTracking(int groupId, boolean tracking){
        if (!hasRepository(groupId))
            return false;
        repositories.get(groupId).setTracking(tracking);
        return true;
    }

    public Collection<GroupInfo> getAllTrackingGroups(){
        var result = new ArrayList<GroupInfo>();
        for (var key : chats.keySet())
            result.add(RepositoryController.getGroupRepository().getGroupById(key));
        return result;
    }

}
