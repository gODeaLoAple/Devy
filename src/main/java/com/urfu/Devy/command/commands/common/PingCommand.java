package main.java.com.urfu.Devy.command.commands.common;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.List;

@CommandName(name = "ping",
        info = "Just pong on your message.",
        detailedInfo = "Just pong on your message.")
public class PingCommand extends Command {
    @Parameter(names = "-h", description = "Will say hello!")
    protected boolean sayHello;

    @Parameter(description = "[[ text ]]")
    protected List<String> text;

    public PingCommand(GroupInfo group, MessageSender sender, String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        var result = new StringBuilder("Pong");
        if (text != null && text.size() != 0)
            result.append(" ").append(String.join(" ", text));
        if (sayHello)
            result.append(" Hello");
        sender.send(result.toString());
    }
}
