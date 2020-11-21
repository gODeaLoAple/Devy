package main.java.com.urfu.Devy.group;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.database.RepositoryController;

import java.util.Collection;

public class GroupInfo {
    protected final String id;

    public GroupInfo(String id) {
        this.id = id;
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

    public void execute(CommandData data, MessageSender sender) {
        CommandsController
                .createCommand(this, sender, data)
                .execute();
    }

    public void sendMessage(String message, MessageSender sender) {
        sender.send(message);
    }

    public Boolean isNull() {
        return false;
    }
}