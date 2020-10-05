package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;

import java.util.List;

@CommandName(name = "ping", info = "c# one love")
public class PingCommand extends Command {
    @Parameter(names = "-f", description = "don't press f")
    protected boolean fuck;

    @Parameter(description = "some text if you want")
    protected List<String> text;

    public PingCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public String execute(CommandData command) {
        parseArgs(command);
        var result = new StringBuilder("Pong ");
        if (text != null && text.size() != 0)
            result.append(String.join(" ", text));
        if (!fuck) {
            return result.toString();
        }
        result.append("FUCK YOURSELF");
        return result.toString();
    }
}
