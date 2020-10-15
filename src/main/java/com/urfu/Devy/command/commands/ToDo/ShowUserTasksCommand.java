package main.java.com.urfu.Devy.command.commands.ToDo;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

@CommandName(name = "showUserTasks")
public class ShowUserTasksCommand extends Command {

    @Parameter
    private String target;

    public ShowUserTasksCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() { // надо как то научиться отправлять это в личку
        var result = new StringBuilder();
        var tasks = group.getAllUserTasks(target);
        for(var task : tasks)
            result.append(task.toDiscordString()).append("\n");
        sender.send(result.toString());
    }
}
