package main.java.com.urfu.Devy.ToDo;

import java.util.HashMap;
import java.util.Map;

public class ToDo {
    private final Map<String, ToDoTask> tasks;
    private final String id;

    public ToDo(String id) {
        this.id = id;
        tasks = new HashMap<>();
    }

    public Map<String, ToDoTask> getTasks() {
        return tasks;
    }
    public ToDoTask getTask(String taskId){
        if(!tasks.containsKey(taskId))
            throw new IllegalArgumentException("Task not found");
        return tasks.get(taskId);
    }

    public String getId() {
        return id;
    }

    public void addTask(ToDoTask task) {
        tasks.put(task.getId(), task);
    }

    public Boolean removeTask(String taskId) {
        return tasks.remove(taskId) != null;
    }

    public boolean hasTask(String taskId) {
        return tasks.containsKey(taskId);
    }
}
