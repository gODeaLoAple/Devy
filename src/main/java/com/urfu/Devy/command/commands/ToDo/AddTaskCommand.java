package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@CommandName(name="addTask", info="")
public class AddTaskCommand extends Command {
    @Parameter(description = "Parameters of task (todo id, task name, author, executor, content)!")
    public ArrayList<String> text;

    public AddTaskCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var name = text.get(0);
        var toDo = group.getToDo(name);
        toDo.addTask(new ToDoTask(text.get(1), text.get(2), text.get(3), text.get(4)));
        sender.send("The task has been added.");
    }

    private void validate() throws CommandException {
        if (text == null || text.size() != 5)
            throw new CommandException("Incorrect count of arguments.");
        if (text.stream().anyMatch(Objects::isNull))
            throw new CommandException("Incorrect arguments!");
    }

}
