package main.java.com.urfu.Devy.group;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.modules.chats.GroupChats;
import main.java.com.urfu.Devy.group.modules.github.GroupGithub;
import main.java.com.urfu.Devy.group.modules.GroupModule;
import main.java.com.urfu.Devy.group.modules.todo.GroupTodo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandsController;


public class GroupInfo {
    protected int id;
    protected GroupChats chats;
    protected GroupTodo todo;
    protected GroupGithub github;

    public GroupInfo(int id) {
        this.id = id;
        setChats(new GroupChats(id));
        setTodo(new GroupTodo(id));
        setGithub(new GroupGithub(id));
    }

    public GroupInfo() {
        this(0);
    }

    public GroupInfo setChats(GroupChats chats) {
        this.chats = chats;
        return setModule(chats);
    }
    public GroupChats asChats() {
        return chats;
    }

    public GroupInfo setTodo(GroupTodo todo) {
        this.todo = todo;
        return setModule(todo);
    }
    public GroupTodo asTodo() {
        return todo;
    }

    public GroupInfo setGithub(GroupGithub github) {
        this.github = github;
        return setModule(github);
    }
    public GroupGithub asGithub() {
        return github;
    }

    private GroupInfo setModule(GroupModule module) {
        module.setGroupId(id);
        return this;
    }

    public int getId() {
        return id;
    }

    public void execute(CommandData data, MessageSender sender) {
        CommandsController
                .createCommand(this, sender, data)
                .execute();
    }

    public void setId(int id) {
        this.id = id;
        chats.setGroupId(id);
        github.setGroupId(id);
        todo.setGroupId(id);
    }

    public void remove() {
        chats.remove();
        RepositoryController.getGroupRepository().removeGroup(this);
    }
}