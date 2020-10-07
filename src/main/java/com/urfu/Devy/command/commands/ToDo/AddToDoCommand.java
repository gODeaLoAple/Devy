package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.ArrayList;

@CommandName(name="addToDo")
public class AddToDoCommand extends Command {

    @Parameter(description = "a")
    public ArrayList<String> args;

    public AddToDoCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        group.addToDo(new ToDo(this.args.get(0)));
        sender.send("Board was added.");
    }
}
