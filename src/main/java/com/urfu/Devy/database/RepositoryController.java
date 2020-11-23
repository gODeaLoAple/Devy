package main.java.com.urfu.Devy.database;

import main.java.com.urfu.Devy.database.repositories.GitHubRepository;
import main.java.com.urfu.Devy.database.repositories.ToDoRepository;
import main.java.com.urfu.Devy.database.repositories.ToDoTaskRepository;

public class RepositoryController {
    private static ToDoRepository todoRepository;
    private static ToDoTaskRepository toDoTaskRepository;
    private static GitHubRepository gitHubRepository;

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

    public static GitHubRepository getGitHubRepository() {
        return gitHubRepository;
    }

    public static void setGitHubRepository(GitHubRepository gitHubRepository) {
        RepositoryController.gitHubRepository = gitHubRepository;
    }
}
