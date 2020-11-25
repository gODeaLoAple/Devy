package test.java.com.urfu.Devy.common;

import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.database.repositories.mocked.MockGithubRepository;
import main.java.com.urfu.Devy.database.repositories.mocked.MockToDoRepository;
import main.java.com.urfu.Devy.database.repositories.mocked.MockToDoTaskRepository;
import org.junit.jupiter.api.BeforeEach;

public abstract class DatabaseIncludeTest {
    @BeforeEach
    public void init() {
        RepositoryController.setToDoTaskRepository(new MockToDoTaskRepository());
        RepositoryController.setTodoRepository(new MockToDoRepository());
        RepositoryController.setGitHubRepository(new MockGithubRepository());
    }
}
