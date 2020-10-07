package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.commands.ToDo.AddTaskCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class EmptySender implements MessageSender {

    @Override
    public void send(String message) {

    }

    @Override
    public String getId() {
        return "";
    }

}

public class AddTaskCommandTest {

    private GroupInfo group;
    private MessageSender sender;

    public AddTaskCommandTest() {
    }

    @BeforeEach
    public void SetUp() {
        group = new GroupInfo("1");
        group.addToDo(new ToDo("1"));
    }


    @Test
    public void throwExceptionWhenNullArguments() {
        assertThrowCommandException(createCommandWithArgs(null));
    }
    @Test
    public void throwExceptionWhenNoArguments() {
        assertThrowCommandException(createCommandWithArgs(new String[0]));
    }

    @Test
    public void throwExceptionWhenIncorrectArgumentsAmount() {
        assertThrowCommandException(createCommandWithArgs(new String[] { "1", "2", "3" }));
    }

    @Test
    public void throwExceptionWhenTaskAlreadyExists() {
        group.getToDo("1").addTask(new ToDoTask("1", "", "", "hello"));
        assertThrowCommandException(createCommandWithArgs(new String[] { "1", "", "", "world" }));
    }

    private void assertThrowCommandException(AddTaskCommand command) {
        Assertions.assertThrows(Exception.class, command::execute);
    }

    private AddTaskCommand createCommandWithArgs(String[] args) {
        return new AddTaskCommand(group, sender, args);
    }



    @Test
    public void addTaskWhenDoesNotExist() {
    }
}
