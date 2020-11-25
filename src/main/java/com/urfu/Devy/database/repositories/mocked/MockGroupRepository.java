package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.database.repositories.implemented.GroupRepository;
import main.java.com.urfu.Devy.group.Group;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MockGroupRepository extends GroupRepository {

    private final Map<Integer, Group> groups = new HashMap<>();


    @Override
    public boolean addGroup(Group group) {
        if (group.getId() != 0)
            return false;
        groups.put(groups.size() + 1, group);
        group.setId(groups.size());
        return  false;
    }

    @Override
    public boolean hasGroup(int id) {
        return groups.containsKey(id);
    }

    @Override
    public boolean removeGroup(Group group) {
        groups.remove(group.getId());
        return true;
    }

    @Override
    public boolean updateGroup(Group group) {
        groups.replace(group.getId(), group);
        return false;
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
