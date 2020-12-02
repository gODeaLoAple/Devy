package test.java.com.urfu.Devy.todo;

import main.java.com.urfu.Devy.group.modules.todo.ToDoTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToDoTaskTest {

    private ToDoTask task;

    @BeforeEach
    public void createTask() {
        task = new ToDoTask("id", "author", "executor", "text");
    }

    @Test
    public void testId() {
        Assertions.assertEquals("id", task.getName());
    }

    @Test
    public void testAuthor() {
        Assertions.assertEquals("author", task.getAuthor());
    }

    @Test
    public void testExecutor() {
        Assertions.assertEquals("executor", task.getExecutor());
    }

    @Test
    public void testText() {
        Assertions.assertEquals("text", task.getText());
    }

    @Test
    public void testGetId() {
        Assertions.assertEquals(0, task.getId());
    }
}
