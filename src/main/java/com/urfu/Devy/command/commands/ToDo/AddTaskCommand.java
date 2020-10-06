package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.List;

@CommandName(name="addTask", info="")
public class AddTaskCommand extends Command {

    @Parameter(description = "Ooh, my finger so deep!")
    protected List<String> text;

    public AddTaskCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {

        var name = text.get(0);
        var toDo = group.getToDo(name);
        toDo.addTask(new ToDoTask(text.get(1), "", "", text.get(2)));

        sender.send("Task has been added.");
    }

}
