package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.commands.ToDo.AddTaskCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.urfu.Devy.Commands.EmptySender;

import javax.swing.*;

public class AddTaskCommandTest {

    private GroupInfo group;
    private EmptySender sender;

    public AddTaskCommandTest() {
    }

    @BeforeEach
    public void SetUp() {
        group = new GroupInfo("1");
        group.addToDo(new ToDo("1"));
        sender = new EmptySender();
    }

    @Test
    public void assertWhenNoArguments() {
        assertHandle(new String[0], "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenIncorrectArgumentsAmount() {
        assertHandle(new String[] { "1", "2", "3" }, "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenTaskWithTheSameIdAlreadyExists() {
        group.getToDo("1").addTask(new ToDoTask("1", "", "", "hello"));
        assertHandle(new String[] { "1", "1", "", "", "hello" }, "The task has been added.");
    }

    @Test
    public void throwExceptionWhenWrongCountOfArguments() {
        assertHandle(new String[] { "1" }, "Incorrect count of arguments.");
        assertHandle(new String[] { "1", "2" }, "Incorrect count of arguments.");
    }

    private void assertHandle(String[] arguments, String handledMessage) {
        new AddTaskCommand(group, sender, arguments).execute();
        sender.assertMessage(handledMessage);
    }

    private AddTaskCommand createCommandWithArgs(String[] args) {
        return new AddTaskCommand(group, sender, args);
    }

    @Test
    public void addTaskWhenDoesNotExist() {
        Assertions.assertDoesNotThrow(() -> {
            createCommandWithArgs(new String[] {"1", "1", "", "", "hello"}).execute();
            Assertions.assertTrue(group.getToDo("1").hasTask("1"));
        });
    }

}
