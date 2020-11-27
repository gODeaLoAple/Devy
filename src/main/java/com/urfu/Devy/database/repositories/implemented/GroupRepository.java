package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.GroupChats;
import main.java.com.urfu.Devy.group.modules.GroupGithub;
import main.java.com.urfu.Devy.group.modules.GroupTodo;
import org.apache.log4j.Logger;


public class GroupRepository extends Repository {

    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    public GroupInfo getGroupById(int groupId) {
        return new GroupInfo(groupId)
                .setChats(new GroupChats(groupId))
                .setGithub(new GroupGithub(groupId))
                .setTodo(new GroupTodo(groupId));
    }

    public void removeGroup(GroupInfo group) {
        throw new IllegalArgumentException();
    }

    public void addGroup(GroupInfo group) {
        throw new IllegalArgumentException();
    }

    public boolean hasGroup(int id) {
        throw new IllegalArgumentException();
    }
}
