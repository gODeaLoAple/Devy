package main.java.com.urfu.Devy.todo;

import java.security.KeyException;
import java.util.HashMap;

public class ToDoBoard {
    private String name;
    private final HashMap<String, ToDoTask> tasks;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ToDoTask getTask(String taskName) throws KeyException {
        if(!tasks.containsKey(taskName))
            throw new KeyException("key not found in tasks dict");
        return tasks.get(taskName);
    }

    public void removeTask(String taskName){
        tasks.remove(taskName);
    }

    public void removeTask(ToDoTask task){
        removeTask(task.getName());
    }

    public Boolean hasTask(String taskName){
        return tasks.containsKey(taskName);
    }

    public void addTask(ToDoTask task){
        tasks.put(task.getName(), task);
    }

    public ToDoBoard(String name){
        this.name = name;
        tasks = new HashMap<>();
    }

    public String show(){
        //return tasks.values().stream().map(ToDoTask::toString).toString(); // посмотреть как работает
        var result = new StringBuilder();
        for(var task : tasks.values())
            result.append(task.toString());

        return result.toString();
    }
}
