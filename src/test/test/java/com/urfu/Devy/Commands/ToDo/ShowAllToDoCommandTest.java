package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.ToDo.ShowAllToDoCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.urfu.Devy.bot.EmptySender;

public class ShowAllToDoCommandTest extends ToDoCommandTest {

    @BeforeEach
    public void setUp() {
        group = new GroupInfo("");
        sender = new EmptySender();
    }

    @Test
    public void handleWhenToDoDoesNotExist() {
        assertHandle(new String[0], "There is no any todo-list.");
    }

    @Test
    public void handleWhenOnlyOneToDoExists() {
        addToDo(group, new ToDo("test1"));
        assertHandle(new String[0], "test1");
    }

    @Test
    public void handleWhenSomeToDoExist() {
        addToDo(group, new ToDo("test1"));
        addToDo(group, new ToDo("test2"));
        assertHandle(new String[0], """
                test1
                test2""");
    }

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new ShowAllToDoCommand(group, sender, args);
    }
}
