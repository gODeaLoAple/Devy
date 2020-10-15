package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@CommandName(name="showAllToDo", info="Show all todo-lists' names.")
public class ShowToDoListsCommand extends Command {

    @Parameter(description = "Arguments: []")
    public List<String> args;

    public ShowToDoListsCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var toDoLists = group.getAllToDo();
        if (toDoLists.size() > 0)
            sender.send(extractLists(toDoLists));
        else
            sender.send("There is no any todo-list");
    }

    private String extractLists(Collection<ToDo> lists) {
        return lists
                .stream()
                .map(ToDo::getId)
                .reduce("", (x,y) -> x.concat("\n").concat(y));
    }

}
