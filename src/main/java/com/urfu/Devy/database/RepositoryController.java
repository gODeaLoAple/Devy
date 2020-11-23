package main.java.com.urfu.Devy.database;

import main.java.com.urfu.Devy.database.repositories.implemented.GroupRepository;
import main.java.com.urfu.Devy.database.repositories.implemented.ToDoRepository;
import main.java.com.urfu.Devy.database.repositories.implemented.ToDoTaskRepository;

public class RepositoryController {
    private static ToDoRepository todoRepository;
    private static ToDoTaskRepository toDoTaskRepository;
    private static GroupRepository groupRepository;


    public static void setTodoRepository(ToDoRepository todoRepository) {
        RepositoryController.todoRepository = todoRepository;
    }
    public static ToDoRepository getTodoRepository() {
        return todoRepository;
    }

    public static void setToDoTaskRepository(ToDoTaskRepository toDoTaskRepository) {
        RepositoryController.toDoTaskRepository = toDoTaskRepository;
    }
    public static ToDoTaskRepository getToDoTaskRepository() {
        return toDoTaskRepository;
    }

    public static void setGroupRepository(GroupRepository groupRepository) {
        RepositoryController.groupRepository = groupRepository;
    }
    public static GroupRepository getGroupRepository() {
        return groupRepository;
    }

}
