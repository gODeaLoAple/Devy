package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.repositories.implemented.GroupRepository;
import main.java.com.urfu.Devy.group.Group;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MockGroupRepository extends GroupRepository {

    private final Map<Integer, Group> groups = new HashMap<>();


    @Override
    public void addGroup(Group group) {
        if (hasGroup(group.getId()))
            updateGroup(group);
        else {
            groups.put(groups.size() + 1, group);
        }
    }

    @Override
    public boolean hasGroup(int id) {
        return groups.containsKey(id);
    }

    @Override
    public void removeGroup(Group group) {
        groups.remove(group.getId());
    }

    @Override
    public void updateGroup(Group group) {
        groups.replace(group.getId(), group);
    }

    @Override
    public Group getGroupByTelegramChatId(Long id) {
        return groups
                .values()
                .stream()
                .filter(x -> Objects.equals(x.getTelegramId(), id))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Group getGroupByDiscordChatId(String id) {
        return groups
                .values()
                .stream()
                .filter(x -> Objects.equals(x.getDiscordId(), id))
                .findFirst()
                .orElseThrow();
    }

}
