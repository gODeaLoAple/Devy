package main.java.com.urfu.Devy.command.commands.todo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.ArrayList;

@CommandName(name="addtodo", info="Add new todo-list to the group.")
public class AddToDoCommand extends Command {

    @Parameter(description = "[todoName]")
    public ArrayList<String> arguments;

    public AddToDoCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            groupInfo.asTodo().addToDo(new ToDo(arguments.get(0)));
            sender.send("The board has been added.");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }

    protected void validate() throws CommandException {
        if (arguments == null)
            throw new CommandException("Incorrect data. Todo name not found.");
    }
}
