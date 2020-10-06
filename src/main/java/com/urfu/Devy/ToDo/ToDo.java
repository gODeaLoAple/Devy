package main.java.com.urfu.Devy.ToDo;

import java.util.HashMap;
import java.util.Map;

public class ToDo {
    private final Map<String, ToDoTask> tasks;
    private final String id;

    public ToDo(String id) {
        this.id = id;
        this.tasks = new HashMap<>();
    }

    public Map<String, ToDoTask> getTasks() {
        return tasks;
    }

    public String getId() {
        return id;
    }

    public void addTask(ToDoTask task) {
        tasks.put(task.getId(), task);
    }

    public void removeTask(String taskId) {
        tasks.remove(taskId);
    }

}
