package test.java.com.urfu.Devy.todo;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.todo.ToDoTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.common.DatabaseIncludeTest;

public class ToDoTest extends DatabaseIncludeTest {

    @Test
    public void testId() {
        var toDo = new ToDo("help");
        Assertions.assertEquals("help", toDo.getName());
    }

    @Test
    public void testTaskAddition() {
        var toDo = new ToDo("help");
        var task = new ToDoTask("test", "", "", "");
        toDo.addTask(task);
        Assertions.assertTrue(toDo.hasTask("test"));
    }

    @Test
    public void testThrowsExceptionOnGetTaskWhenDoesNotExist() {
        var todo = new ToDo("help");
        Assertions.assertThrows(IllegalArgumentException.class, () -> todo.getTask("task"));
    }

    @Test
    public void testGetTaskWhenExists() {
        var todo = new ToDo("help");
        var task = new ToDoTask("task", "", "", "");
        todo.addTask(task);
        Assertions.assertEquals(task, todo.getTask("task"));
    }

}
