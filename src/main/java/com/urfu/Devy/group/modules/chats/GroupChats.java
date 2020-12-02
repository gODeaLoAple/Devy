package main.java.com.urfu.Devy.group.modules.chats;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.group.modules.GroupModule;

public class GroupChats extends GroupModule {

    public GroupChats(int groupId) {
        super(groupId);
    }

    public Chats getChats() {
        return RepositoryController.getChatsRepository().getChatsByGroupId(groupId);
    }

    public void create() {
        RepositoryController.getChatsRepository().addChats(groupId);
    }

    public void remove() {
        RepositoryController.getChatsRepository().removeChats(this);
    }

}

