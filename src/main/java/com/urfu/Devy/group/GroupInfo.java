package main.java.com.urfu.Devy.group;
import main.java.com.urfu.Devy.github.RepositoryInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.database.RepositoryController;

import java.util.*;

public class GroupInfo {
    protected final String id;
    protected final HashMap<String, MessageSender> senders;
    private Timer repositoryTimer;

    public GroupInfo(String id) {
        this.id = id;
        senders = new HashMap<>();
    }

    public void addSender(MessageSender sender) throws CommandException {
        var senderId = sender.getId();
        if (senders.containsKey(senderId))
            throw new CommandException("The channel had already been added.");
        senders.put(sender.getId(), sender);
    }

    public void removeSender(MessageSender sender) throws CommandException {
        var senderId = sender.getId();
        if (!senders.containsKey(senderId))
            throw new CommandException("The channel was not found.");
        senders.remove(senderId);
    }

    public MessageSender getSender(String id){
        return senders.get(id);
    }

    public void addToDo(ToDo toDo) throws CommandException {
        if (!RepositoryController.getTodoRepository().addToDoList(id, toDo))
            throw new CommandException("The ToDo list was already added.");
    }

    public Boolean removeToDo(ToDo toDo) throws CommandException {
        return removeToDo(toDo.getName());
    }

    public Boolean removeToDo(String toDoId) throws CommandException {
        if (!RepositoryController.getTodoRepository().removeToDoList(id, toDoId))
            throw new CommandException("The ToDo list %s was not founded.".formatted(toDoId));
        return false;
    }

    public ToDo getToDo(String toDoId) throws CommandException {
        var toDo = RepositoryController.getTodoRepository().getToDoByName(id, toDoId);
        if (toDo == null)
            throw new CommandException("The ToDo list \"%s\" was not founded.".formatted(toDoId));
        return toDo;
    }

    public Collection<ToDo> getAllToDo() {
        return RepositoryController
                .getTodoRepository()
                .getAllToDo(getId());
    }
    public String getId() {
        return id;
    }

    public void execute(CommandData data, String senderId) {
        CommandsController
                .createCommand(this, senders.get(senderId), data)
                .execute();
    }

    public void sendMessage(String message, String senderId) {
        if (!senders.containsKey(senderId))
            throw new IllegalArgumentException("The channel was not founded.");
        senders.get(senderId).send(message);
    }

    public void addRepository(String name, String repository, String chatId) throws CommandException {
        if (!RepositoryController.getGitHubRepository().addRepository(id, chatId, new RepositoryInfo(name, repository)))
            throw new CommandException("Repository was already added.");
    }

    public void removeRepository() throws CommandException{
        if(!RepositoryController.getGitHubRepository().removeRepository(id))
            throw new CommandException("No such repository");
    }

    public Boolean hasRepository(){
        return RepositoryController.getGitHubRepository().hasRepository(id);
    }

    public String getLastCommitDate(){
        return RepositoryController.getGitHubRepository().getLastCommitDate(id);
    }

    public void startTrack(Timer timer){
        repositoryTimer = timer;
        RepositoryController.getGitHubRepository().setTracking(id, true);
    }

    public void stopTrack() throws CommandException{
        if(repositoryTimer == null)
            throw new CommandException("you haven't tracking repository");
        repositoryTimer.cancel();
        RepositoryController.getGitHubRepository().setTracking(id, false);
    }
}