package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.modules.todo.ToDoTask;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.ArrayList;
import java.util.stream.Collectors;

@CommandName(name="showtasks", info="Shows all tasks in todo-list.")
public class ShowTasksCommand extends Command {

    @Parameter(description = "[todo]")
    protected ArrayList<String> text;

    public ShowTasksCommand(GroupInfo info, MessageSender sender, String[] args) {
        super(info, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var toDo = groupInfo.asTodo().getToDo(text.get(0));
            var message = toDo
                    .getTasks()
                    .stream()
                    .map(ToDoTask::getInfo)
                    .collect(Collectors.joining("\n\n"));
            if (message.isEmpty() || message.isBlank())
                message = "There is no any task in this ToDo list.";
            sender.send(message);
        } catch (IllegalArgumentException | CommandException e) {
            sender.send(e.getMessage());
        }
    }

    private void validate() throws CommandException {
        if (text == null || text.size() != 1)
            throw new CommandException("Incorrect count of arguments. Todo name not found.");
    }

}