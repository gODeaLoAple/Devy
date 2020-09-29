package main.java.com.urfu.Devy.Command.Commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Command;
import main.java.com.urfu.Devy.Command.CommandData;

public class PingCommand extends Command {
    @Parameter(names = {"-f, --fuck"}, description = "don't press f")
    protected boolean fuck;

    @Parameter(description = "some text if you want")
    protected String[] text;

    public PingCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public String execute(CommandData command) {
        var result = new StringBuilder("Pong ");
        if(text != null && text.length != 0)
            result.append(String.join(" ", text));
        if(fuck)
            result.append("FUCK YOURSELF");
        return result.toString();
    }

    @Override
    public String getName() {
        return "Ping";
    }

    @Override
    public String getInfo() {
        return "send ping to get pong response";
    }
}
