package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.database.repositories.implemented.ToDoRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MockToDoRepository extends ToDoRepository {
    private final HashMap<Integer, ArrayList<ToDo>> todoList = new HashMap<>();

    @Override
    public boolean addToDoList(int groupId, ToDo todo) {
        if (!todoList.containsKey(groupId))
            todoList.put(groupId, new ArrayList<>());
        if (isToDoExists(groupId, todo.getName()))
            return false;
        todoList.get(groupId).add(todo);
        return true;
    }

    @Override
    public boolean removeToDoList(int todoId) {
        for (var groupId : todoList.keySet())
            todoList.get(groupId).removeIf(x -> x.getId() == todoId);
        return true;
    }

    @Override
    public ToDo getToDoByName(int groupId, String todoName) {
        return todoList
                .getOrDefault(groupId, new ArrayList<>())
                .stream()
                .filter(x -> x.getName().equals(todoName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<ToDo> getAllToDo(int groupId) {
        return todoList.getOrDefault(groupId, new ArrayList<>());
    }

    private boolean isToDoExists(int groupId, String todoName) {
        return todoList.containsKey(groupId)
                && todoList.get(groupId).stream().anyMatch(x -> x.getName().equals(todoName));
    }
}
