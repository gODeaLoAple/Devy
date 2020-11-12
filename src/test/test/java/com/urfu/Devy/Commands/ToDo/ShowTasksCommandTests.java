package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.todo.ToDoTask;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.commands.todo.ShowTasksCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.urfu.Devy.bot.EmptySender;

public class ShowTasksCommandTests extends ToDoCommandTest {

    @BeforeEach
    public void setUp() {
        sender = new EmptySender();
        group = new GroupInfo("");
    }

    @Test
    public void handleWhenIncorrectCountOfArgs() {
        assertHandle(new String[0], "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenNoTasks() {
        try {
            group.addToDo(new ToDo("1"));
            assertHandle(new String[] { "1" }, "There is no any task in this ToDo list.");
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    @Test
    public void handleWhenToDoDoesNotExists() {
        assertHandle(new String[] { "1" }, "The ToDo list \"1\" was not founded.");
    }

    @Test
    public void showAllTaskWhenExist() {
        var toDo = new ToDo("1");
        toDo.addTask(new ToDoTask("task1", "author1", "executor1", "text1"));
        toDo.addTask(new ToDoTask("task2", "author2", "executor2", "text2"));
        try {
            group.addToDo(toDo);
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
        assertHandle(new String[] {"1"}, """
                **Name**: *task1*
                **Author**: *author1*
                **Executor**: *executor1*
                **Task**: *text1*
                                
                **Name**: *task2*
                **Author**: *author2*
                **Executor**: *executor2*
                **Task**: *text2*""");
    }


    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new ShowTasksCommand(group, sender, args);
    }
}
