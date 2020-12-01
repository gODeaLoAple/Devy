package main.java.com.urfu.Devy.group.modules.todo;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.modules.GroupModule;
import main.java.com.urfu.Devy.group.modules.todo.ToDo;

import java.util.Collection;

public class GroupTodo extends GroupModule {

    public GroupTodo(int groupId) {
        super(groupId);
    }


    public void addToDo(ToDo toDo) throws CommandException {
        if (!RepositoryController.getTodoRepository().addToDoList(groupId, toDo))
            throw new CommandException("The ToDo list was already added.");
    }

    public Boolean removeToDo(ToDo toDo) throws CommandException {
        return removeToDo(toDo.getName());
    }

    public Boolean removeToDo(String todoName) throws CommandException {
        if (!RepositoryController.getTodoRepository().removeToDoList(getToDo(todoName).getId()))
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
