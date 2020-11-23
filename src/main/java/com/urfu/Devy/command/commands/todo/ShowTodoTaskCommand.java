package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;


import java.util.List;

@CommandName(name = "showtask", info="Show the task from todo-list.")
public class ShowTodoTaskCommand extends Command {

    @Parameter(description = "[todoName] [taskName]")
    private List<String> text;

    public ShowTodoTaskCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            var todo = groupInfo.getToDo(text.get(0));
            var task = todo.getTask(text.get(1));
            sender.send(task.getInfo());
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }

    }
}
