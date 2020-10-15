package test.java.com.urfu.Devy.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ToDoTest {

    @Test
    public void testId() {
        var toDo = new ToDo("help");
        Assertions.assertEquals("help", toDo.getId());
    }

    @Test
    public void testTaskAddition() {
        var toDo = new ToDo("help");
        var task = new ToDoTask("test", "", "", "");
        toDo.addTask(task);

        Assertions.assertTrue(toDo.hasTask("test"));
        Assertions.assertTrue(toDo.getTasks().containsKey("test"));
    }

}
