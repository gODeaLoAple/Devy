package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@CommandName(name="showalltodo", info="Show all todo-lists' names.")
public class ShowAllToDoCommand extends Command {

    @Parameter
    public List<String> args;

    public ShowAllToDoCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var toDoLists = groupInfo.getAllToDo();
        if (toDoLists.size() > 0)
            sender.send(extractLists(toDoLists));
        else
            sender.send("There is no any todo-list.");
    }

    private String extractLists(Collection<ToDo> lists) {
        return lists
                .stream()
                .map(ToDo::getName)
                .reduce((x,y) -> x.concat("\n").concat(y))
                .orElse("");
    }

}
