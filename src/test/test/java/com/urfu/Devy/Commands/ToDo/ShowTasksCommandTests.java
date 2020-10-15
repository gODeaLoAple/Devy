package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.command.commands.ToDo.ShowTasksCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.Commands.EmptySender;

public class ShowTasksCommandTests {

    private EmptySender sender;
    private GroupInfo group;

    @BeforeEach
    public void setUp() {
        sender = new EmptySender();
        group = new GroupInfo("");
    }

    @Test
    public void handleWhenNoTasks() {
        group.addToDo(new ToDo("1"));
        assertHandle(new String[] { "1" }, "There is no task in this ToDo list.");
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
        group.addToDo(toDo);
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

    private ShowTasksCommand createCommandWithArgs(String[] args) {
        return new ShowTasksCommand(group, sender, args);
    }

    private void assertHandle(String[] arguments, String handledMessage) {
        createCommandWithArgs(arguments).execute();
        sender.assertMessage(handledMessage);
    }

}
