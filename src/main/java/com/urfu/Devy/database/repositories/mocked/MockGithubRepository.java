package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.repositories.GitHubRepository;
import main.java.com.urfu.Devy.github.GroupWithChatId;
import main.java.com.urfu.Devy.github.RepositoryInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockGithubRepository extends GitHubRepository {

    private final Map<String, RepositoryInfo> repositories = new HashMap<>();
    private final Map<String, String> chats = new HashMap<>();
    private final Map<String, String> commits = new HashMap<>();

    public boolean addRepository(String groupId, String chatId, RepositoryInfo repository) {
        if (repositories.containsKey(groupId))
            return false;
        repositories.put(groupId, repository);
        chats.put(groupId, chatId);
        return true;
    }

    public boolean removeRepository(String groupId) {
        if (!repositories.containsKey(groupId))
            return false;
        repositories.remove(groupId);
        chats.remove(groupId);
        return true;
    }

    public RepositoryInfo getRepository(String groupId) {
        return repositories.get(groupId);
    }

    public Collection<RepositoryInfo> getAllRepositories() {
        return repositories.values();
    }

    public boolean hasRepository(String groupId) {
        return repositories.containsKey(groupId);
    }

    public String getLastCommitDate(String groupId){
        return commits.get(groupId);
    }

    public boolean setLastCommitDate(String groupId, String date){
        commits.put(groupId, date);
        return false;
    }

    public boolean setTracking(String groupId, boolean tracking){
        if (!hasRepository(groupId))
            return false;
        repositories.get(groupId).setTracking(tracking);
        return true;
    }

    public Collection<GroupWithChatId> getAllTrackingGroups(){
        var result = new ArrayList<GroupWithChatId>();
        for (var key : chats.keySet())
            result.add(new GroupWithChatId(key, chats.get(key)));
        return result;
    }

}
