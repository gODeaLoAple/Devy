package main.java.com.urfu.Devy.database;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.database.repositories.implemented.*;

public class RepositoryController {
    private static ToDoRepository todoRepository;
    private static ToDoTaskRepository toDoTaskRepository;
    private static GroupRepository groupRepository;
    private static GitHubRepository gitHubRepository;
    private static ChatsRepository chatsRepository;

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

    public static void setGitHubRepository(GitHubRepository gitHubRepository) {
        RepositoryController.gitHubRepository = gitHubRepository;
    }
    public static GitHubRepository getGitHubRepository() {
        return gitHubRepository;
    }


    public static void setChatsRepository(ChatsRepository chatsRepository) {
        RepositoryController.chatsRepository = chatsRepository;
    }
    public static ChatsRepository getChatsRepository() {
        return chatsRepository;
    }
}
