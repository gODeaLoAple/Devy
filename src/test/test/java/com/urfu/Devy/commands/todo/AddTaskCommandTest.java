package test.java.com.urfu.Devy.commands.todo;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.todo.ToDoTask;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.commands.todo.AddTaskCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.urfu.Devy.sender.EmptySender;
public class AddTaskCommandTest extends ToDoCommandTest {

    @BeforeEach
    public void setUp() {
        group = new GroupInfo(0);
        addToDo(group, new ToDo("1"));
        sender = new EmptySender();
    }

    @Test
    public void handleWhenTaskWithTheSameIdAlreadyExists() {
        try {
            group.asTodo().getToDo("1").addTask(new ToDoTask("1", "", "", "hello"));
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
        assertHandle(new String[] { "1", "1", "", "", "hello" }, "The task has been added.");
    }

    @Test
    public void handleWhenToDoDoesNotExists() {
        assertHandle(new String[] { "2", "1", "", "", "hello"}, "The ToDo list \"2\" was not founded.");
    }

    @Test
    public void throwExceptionWhenWrongCountOfArguments() {
        assertHandle(new String[0], "Incorrect count of arguments. Todo name not found.");
        assertHandle(new String[] { "1" }, "Incorrect count of arguments. Task name not found.");
        assertHandle(new String[] { "1", "2" }, "Incorrect count of arguments. Author not found.");
        assertHandle(new String[] { "1", "2", "3" }, "Incorrect count of arguments. Executor name not found.");
        assertHandle(new String[] { "1", "2", "3", "4" }, "Incorrect count of arguments. Content not found.");
    }

    @Test
    public void addTaskWhenDoesNotExist() {
        Assertions.assertDoesNotThrow(() -> {
            createCommandWithArgs(new String[] {"1", "1", "", "", "hello"}).execute();
            Assertions.assertTrue(group.asTodo().getToDo("1").hasTask("1"));
        });
    }

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new AddTaskCommand(group, sender, args);
    }
}
