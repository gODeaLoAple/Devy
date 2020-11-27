package test.java.com.urfu.Devy.commands.todo;

import main.java.com.urfu.Devy.group.modules.GroupTodo;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.commands.todo.ShowAllToDoCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.urfu.Devy.sender.EmptySender;

public class ShowAllToDoCommandTest extends ToDoCommandTest {

    @Test
    public void handleWhenToDoDoesNotExist() {
        group.setTodo(new GroupTodo());
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
