package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;

import main.java.com.urfu.Devy.todo.ToDoBoard;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@CommandName(name = "addBoard", info = "wee-weee")
public class AddBoardCommand extends Command {
    private static final Logger log = Logger.getLogger(AddBoardCommand.class);

    @Parameter()
    private List<String> name = new ArrayList<>();

    public AddBoardCommand(HelperBot bot){
        super(bot);
    }
    @Override
    public String execute(CommandData command) {
        parseArgs(command);
        if(name.isEmpty())
            return "Enter board name";
        if(name.size() > 1)
            return "too mach args";
        var group = bot.getGroup();
        if(group.hasToDoBoard(name.get(0)))
            return "board with this name already exists";
        group.addToDoBoard(new ToDoBoard(name.get(0)));
        return "board has been added: " + name.get(0);
    }
}
