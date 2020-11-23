package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.Group;
import org.apache.log4j.Logger;

public class GroupRepository extends Repository {

    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    public void addGroup(Group group) {

    }

    public void updateGroup(Group group) {

    }

    public void removeGroup(Group group) {

    }

    public Group getGroupByTelegramChatId(Long id) {
        throw new IllegalArgumentException();
    }

    public Group getGroupByDiscordChatId(String id) {
        throw new IllegalArgumentException();
    }

    public boolean hasGroup(int groupId) {
        throw new IllegalArgumentException();
    }
}
