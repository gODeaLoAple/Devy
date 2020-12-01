package main.java.com.urfu.Devy.group.modules.chats;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.modules.GroupModule;

public class GroupChats extends GroupModule {

    public GroupChats(int groupId) {
        super(groupId);
    }

    public Chats getChats() {
        return RepositoryController.getChatsRepository().getChatsByGroupId(groupId);
    }

    public void addChats() {
        RepositoryController.getChatsRepository().addChats(groupId);
    }

    public boolean hasChats() {
        return RepositoryController.getChatsRepository().hasChats(groupId);
    }
}

