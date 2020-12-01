package main.java.com.urfu.Devy.group.modules.todo;

import main.java.com.urfu.Devy.database.RepositoryController;
import java.util.ArrayList;

public class ToDo {
    private final String name;
    private final int id;

    public ToDo(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public ToDo(String name) {
        this(0, name);
    }


    public ArrayList<ToDoTask> getTasks() {
        return RepositoryController.getToDoTaskRepository().getAllTasksInToDo(id);
    }

    public ToDoTask getTask(String taskName) {
        var task = RepositoryController.getToDoTaskRepository().getTaskByName(id, taskName);
        if (task == null)
            throw new IllegalArgumentException("Task not found");
        return task;
    }

    public String getName() {
        return name;
    }

    public boolean addTask(ToDoTask task) {
        return RepositoryController.getToDoTaskRepository().addTask(id, task);
    }

    public boolean removeTask(String taskName) {
        return RepositoryController.getToDoTaskRepository().removeTaskByName(id, taskName);
    }

    public boolean hasTask(String taskName) {
        return RepositoryController.getToDoTaskRepository().hasTaskWithName(id, taskName);
    }

    public int getId() {
        return id;
    }
}
