package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.modules.todo.ToDo;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@CommandName(name="showalltodo", info="Show all todo-lists' names.")
public class ShowAllToDoCommand extends Command {

    @Parameter
    public List<String> args;

    public ShowAllToDoCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var toDoLists = groupInfo.asTodo().getAllToDo();
        if (toDoLists.size() > 0)
            sender.send(extractLists(toDoLists.stream().map(ToDo::getName)));
        else
            sender.send("There is no any todo-list.");
    }

    private String extractLists(Stream<String> lists) {
        var sb = new StringBuilder();
        final int[] count = {1};
        lists.sorted().forEach(x -> {
            sb.append(count[0]).append(") ").append(x).append(System.lineSeparator());
            count[0]++;
        });
        return sb.toString();
    }

}
