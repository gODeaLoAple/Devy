package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.repositories.implemented.GroupRepository;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.GroupChats;
import main.java.com.urfu.Devy.group.modules.GroupGithub;
import main.java.com.urfu.Devy.group.modules.GroupTodo;

import java.util.HashMap;
import java.util.Map;

public class MockGroupRepository extends GroupRepository {

    private final Map<Integer, GroupInfo> groups = new HashMap<>();

    @Override
    public GroupInfo getGroupById(int groupId) {
        return groups.get(groupId);
    }

    @Override
    public void removeGroup(GroupInfo group) {
        groups.remove(group.getId());
    }

    @Override
    public void addGroup(GroupInfo group) {
        groups.put(group.getId(), group);
    }

    @Override
    public boolean hasGroup(int id) {
        return groups.containsKey(id);
    }

}
