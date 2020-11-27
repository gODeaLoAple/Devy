package main.java.com.urfu.Devy.group.modules;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.todo.ToDo;

import java.util.Collection;

public class GroupTodo extends GroupModule{

    public GroupTodo(int groupId) {
        super(groupId);
    }

    public GroupTodo() {
        this(0);
    }

    public void addToDo(ToDo toDo) throws CommandException {
        if (!RepositoryController.getTodoRepository().addToDoList(groupId, toDo))
            throw new CommandException("The ToDo list was already added.");
    }

    public Boolean removeToDo(ToDo toDo) throws CommandException {
        return removeToDo(toDo.getName());
    }

    public Boolean removeToDo(String todoName) throws CommandException {
        if (!RepositoryController.getTodoRepository().removeToDoList(groupId, todoName))
            throw new CommandException("The ToDo list %s was not founded.".formatted(todoName));
        return false;
    }

    public ToDo getToDo(String toDoId) throws CommandException {
        var toDo = RepositoryController.getTodoRepository().getToDoByName(groupId, toDoId);
        if (toDo == null)
            throw new CommandException("The ToDo list \"%s\" was not founded.".formatted(toDoId));
        return toDo;
    }

    public Collection<ToDo> getAllToDo() {
        return RepositoryController
                .getTodoRepository()
                .getAllToDo(groupId);
    }

}
