package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;

import java.util.ArrayList;
import java.util.List;

@CommandName(name = "showTask")
public class ShowTodoTaskCommand extends Command {

    @Parameter(description = "what")
    private List<String> text;
    public ShowTodoTaskCommand(GroupInfo group, MessageSender sender, String[] args) throws ParseCommandException {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var todo= group.getToDo(text.get(0));
        var task = todo.getTask(text.get(1));
        sender.send(task.toString());
    }
}
