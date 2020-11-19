package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.todo.ToDoTask;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.commands.todo.RemoveTaskCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.urfu.Devy.sender.EmptySender;

public class RemoveTaskCommandTest extends ToDoCommandTest {

    @BeforeEach
    public void setUp() {
        sender = new EmptySender();
        group = new GroupInfo("");
    }

    @Test
    public void handleWhenIncorrectArgsCount() {
        assertHandle(new String[0], "Incorrect count of arguments.");
        assertHandle(new String[] {"1"}, "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenToDoWhenToDoDoesNotExist() {
        assertHandle(new String[] {"test", "test"}, "The ToDo list \"test\" was not founded.");
    }

    @Test
    public void handleWhenTaskDoesNotExist() {
        addToDo(group, new ToDo("test"));
        assertHandle(new String[] {"test", "test"}, "The task \"test\" does not exist in \"test\".");
    }

    @Test
    public void handleWhenTaskExists() {
        var todo = new ToDo("test");
        todo.addTask(new ToDoTask("test", "", "", "text"));
        addToDo(group, todo);
        assertHandle(new String[] {"test", "test"}, "The task \"test\" has been removed from \"test\".");
        try {
            Assertions.assertFalse(group.getToDo("test").hasTask("test"));
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new RemoveTaskCommand(group, sender, args);
    }
}
