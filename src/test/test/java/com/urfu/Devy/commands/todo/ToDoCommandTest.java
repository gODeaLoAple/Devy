package test.java.com.urfu.Devy.commands.todo;

import main.java.com.urfu.Devy.group.modules.GroupTodo;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.CommandException;
import test.java.com.urfu.Devy.commands.CommandTester;

public abstract class ToDoCommandTest extends CommandTester {

    protected void addToDo(GroupInfo group, ToDo todo) {
        try {
            if (group.asTodo() == null)
                group.setTodo(new GroupTodo());
            group.asTodo().addToDo(todo);
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
