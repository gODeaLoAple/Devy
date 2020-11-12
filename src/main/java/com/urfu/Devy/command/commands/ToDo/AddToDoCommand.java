package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.ArrayList;

@CommandName(name="addToDo", info="Add new todo-list to the group.")
public class AddToDoCommand extends Command {

    @Parameter(description = "[todoName]")
    public ArrayList<String> arguments;

    public AddToDoCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            group.addToDo(new ToDo(arguments.get(0)));
            sender.send("The board has been added.");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
