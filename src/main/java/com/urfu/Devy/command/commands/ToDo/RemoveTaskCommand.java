package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandName(name = "rmTask", info="Remove task from todo-list.")
public class RemoveTaskCommand extends Command {

    @Parameter(description = "[todoName] [taskName]")
    private List<String> text;

    public RemoveTaskCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var todoId = text.get(0);
            var taskId = text.get(1);
            var todo= group.getToDo(todoId);
            if (todo.removeTask(taskId))
                sender.send("The task \"%s\" has been removed from \"%s\".".formatted(taskId, todoId));
            else
                sender.send("The task \"%s\" does not exist in \"%s\".".formatted(taskId, todoId));
        }
        catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    private void validate() throws CommandException {
        if (text == null || text.size() != 2)
            throw new CommandException("Incorrect count of arguments.");
    }
}
