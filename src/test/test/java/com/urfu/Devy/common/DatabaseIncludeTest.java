package test.java.com.urfu.Devy.common;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.mocked.*;
import org.junit.jupiter.api.BeforeEach;

public abstract class DatabaseIncludeTest {
    @BeforeEach
    public void init() {
        RepositoryController.setToDoTaskRepository(new MockToDoTaskRepository());
        RepositoryController.setTodoRepository(new MockToDoRepository());
        RepositoryController.setGroupRepository(new MockGroupRepository());
        RepositoryController.setGitHubRepository(new MockGithubRepository());
        RepositoryController.setChatsRepository(new MockChatsRepository());
    }
}
