package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandName(name = "rmTask")
public class RemoveTaskCommand extends Command {

    @Parameter
    private List<String> text;

    public RemoveTaskCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var todo= group.getToDo(text.get(0));
        todo.removeTask(text.get(1));
        sender.send("task \"%s\" has been removed from \"%s\"".formatted(text.get(1), text.get(0)));
    }
}
