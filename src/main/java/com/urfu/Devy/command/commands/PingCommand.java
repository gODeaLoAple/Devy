package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;

import java.util.List;

@CommandName(name = "ping", info = "c# one love")
public class PingCommand extends Command {
    @Parameter(names = "-f", description = "don't press f")
    protected boolean fuck;

    @Parameter(description = "some text if you want")
    protected List<String> text;

    public PingCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var result = new StringBuilder("Pong ");
        if (text != null && text.size() != 0)
            result.append(String.join(" ", text));
        if (!fuck) {
            sender.send(result.toString());
        }
        result.append(" FUCK YOURSELF");
        sender.send(result.toString());
    }
}
