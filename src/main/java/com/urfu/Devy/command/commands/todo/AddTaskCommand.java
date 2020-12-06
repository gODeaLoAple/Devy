package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.modules.todo.ToDoTask;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.ArrayList;
import java.util.Objects;

@CommandName(name="addtask", info="Add new task to todo-list.")
public class AddTaskCommand extends Command {

    @Parameter(description = "[todoName] [taskName] [author] [executor] [content]")
    public ArrayList<String> text;

    public AddTaskCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var name = text.get(0);
            var todo = groupInfo.asTodo().getToDo(name);
            todo.addTask(new ToDoTask(text.get(1), text.get(2), text.get(3), text.get(4)));
            sender.send("The task has been added.");
        }
        catch (IllegalArgumentException | CommandException e) {
            sender.send(e.getMessage());
        }
    }

    private void validate() throws CommandException {
        if (text == null || text.size() == 0)
            throw new CommandException("Incorrect count of arguments. Todo name not found.");
        if (text.size() == 1)
            throw new CommandException("Incorrect count of arguments. Task name not found.");
        if (text.size() == 2)
            throw new CommandException("Incorrect count of arguments. Author not found.");
        if (text.size() == 3)
            throw new CommandException("Incorrect count of arguments. Executor name not found.");
        if (text.size() == 4)
            throw new CommandException("Incorrect count of arguments. Content not found.");
        if (text.stream().anyMatch(Objects::isNull))
            throw new CommandException("Incorrect arguments!");
    }

}
