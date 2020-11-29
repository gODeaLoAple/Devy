package main.java.com.urfu.Devy.group;
import main.java.com.urfu.Devy.group.modules.GroupChats;
import main.java.com.urfu.Devy.group.modules.GroupGithub;
import main.java.com.urfu.Devy.group.modules.GroupModule;
import main.java.com.urfu.Devy.group.modules.GroupTodo;
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
        setChats(new GroupChats());
        setTodo(new GroupTodo());
        setGithub(new GroupGithub());
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

    public void sendMessage(String message, MessageSender sender) {
        sender.send(message);
    }

}