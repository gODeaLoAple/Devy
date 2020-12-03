package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandName(name="rmtodo", info="Remove todo with name")
public class RemoveTodoCommand extends Command {

    @Parameter(names="-a", description = "Remove all todo-lists")
    private boolean all;


    @Parameter(description = "[todoName]")
    private List<String> text;

    public RemoveTodoCommand(GroupInfo groupInfo, MessageSender sender, @NotNull String[] args) {
        super(groupInfo, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            if (all)
                removeAllTodo();
            else
                removeTodoWithName(text.get(0));
            sender.send("Todo removed.");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    private void removeTodoWithName(String name) throws CommandException {
        groupInfo.asTodo().removeToDo(name);
    }

    private void removeAllTodo() {
        groupInfo.asTodo().removeAllTodo();
    }

    private void validate() throws CommandException {
        if (text == null || !all && text.size() != 1)
            throw new CommandException("Incorrect count of arguments. Todo-list name not found.");
    }
}
