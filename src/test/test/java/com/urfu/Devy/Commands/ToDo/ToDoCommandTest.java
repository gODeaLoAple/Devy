package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.command.CommandException;
import test.java.com.urfu.Devy.Commands.CommandTest;

public abstract class ToDoCommandTest extends CommandTest {

    protected void addToDo(GroupInfo group, ToDo todo) {
        try {
            group.addToDo(todo);
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
