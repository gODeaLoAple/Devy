package main.java.com.urfu.Devy.database.repositories.mocked;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.database.repositories.ToDoRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MockToDoRepository extends ToDoRepository {
    private final HashMap<String, ArrayList<ToDo>> todoList = new HashMap<>();

    @Override
    public boolean addToDoList(String groupId, ToDo todo) {
        if (!todoList.containsKey(groupId))
            todoList.put(groupId, new ArrayList<>());
        if (isToDoExists(groupId, todo.getName()))
            return false;
        todoList.get(groupId).add(todo);
        return true;
    }

    @Override
    public boolean removeToDoList(String groupId, String todoName) {
        if (!isToDoExists(groupId, todoName))
            return false;
        todoList.get(groupId).removeIf(x -> x.getName().equals(todoName));
        return true;
    }

    @Override
    public ToDo getToDoByName(String groupId, String todoName) {
        return todoList
                .getOrDefault(groupId, new ArrayList<>())
                .stream()
                .filter(x -> x.getName().equals(todoName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<ToDo> getAllToDo(String groupId) {
        return todoList.getOrDefault(groupId, new ArrayList<>());
    }

    private boolean isToDoExists(String groupId, String todoName) {
        return todoList.containsKey(groupId)
                && todoList.get(groupId).stream().anyMatch(x -> x.getName().equals(todoName));
    }
}
