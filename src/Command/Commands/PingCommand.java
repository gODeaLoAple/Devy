package Command.Commands;

import Bot.HelperBot;
import Command.*;
import com.beust.jcommander.Parameter;

@CommandAnnotation(name = "ping")
public class PingCommand extends Command {
    @Parameter(names = {"-f, --fuck"}, description = "don't press f")
    protected boolean fuck;

    @Parameter(description = "some text if you want")
    protected String[] text;

    public PingCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public void execute(CommandData command){
        super.execute(command);
        var result = new StringBuilder("Pong ");
        if (text != null && text.length != 0)
            result.append(String.join(" ", text));
        if (fuck)
            result.append("FUCK YOURSELF");
        bot.send(result.toString());
    }
}
