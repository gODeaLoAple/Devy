package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

@CommandName(name = "showusertasks",
        info = "Shows all user tasks from all todo-lists.",
        detailedInfo = "Shows all user tasks from all todo-lists")
public class ShowUserTasksCommand extends Command {

    @Parameter
    private String target;

    public ShowUserTasksCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var result = new StringBuilder();
        var tasks = group
                .getAllToDo()
                .stream()
                .map(ToDo::getTasks)
                .flatMap(ArrayList::stream)
                .filter(x -> x.getExecutor().equals(target))
                .collect(Collectors.toList());
        if (tasks.size() == 0)
            result.append("No user with name: \"%s\"".formatted(target));
        else {
            for(var task : tasks)
                result.append(task.getInfo()).append("\n\n");
        }

        sender.send(result.toString());
    }
}
