package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.group.modules.todo.ToDoTask;
import main.java.com.urfu.Devy.database.repositories.implemented.ToDoTaskRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class MockToDoTaskRepository extends ToDoTaskRepository {

    private final HashMap<Integer, ArrayList<ToDoTask>> tasks = new HashMap<>();

    @Override
    public boolean addTask(int todoId, ToDoTask task) {
        if (!tasks.containsKey(todoId))
            tasks.put(todoId, new ArrayList<>());
        if (isTaskExists(todoId, task.getName()))
            return false;
        tasks.get(todoId).add(task);
        return true;
    }

    @Override
    public boolean removeTaskByName(int todoId, String taskName) {
        if (!isTaskExists(todoId, taskName))
            return false;
        tasks.get(todoId).removeIf(x -> x.getName().equals(taskName));
        return true;
    }

    @Override
    public ArrayList<ToDoTask> getAllTasksInToDo(int toDoId) {
        return tasks.getOrDefault(toDoId, new ArrayList<>());
    }

    @Override
    public ToDoTask getTaskByName(int todoId, String taskName) {
        return tasks
                .getOrDefault(todoId, new ArrayList<>())
                .stream()
                .filter(x -> x.getName().equals(taskName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean hasTaskWithName(int todoId, String taskName) {
        return isTaskExists(todoId, taskName);
    }

    private boolean isTaskExists(int todoId, String taskName) {
        return tasks.containsKey(todoId) && tasks.get(todoId).stream().anyMatch(x -> x.getName().equals(taskName));
    }
}
