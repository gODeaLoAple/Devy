package Command;

import Bot.HelperBot;
import com.beust.jcommander.Parameter;

public class PingCommand extends Command {
    @Parameter(names = {"-f, --fuck"}, description = "don't press f")
    protected boolean fuck;

    @Parameter(description = "some text if you want")
    protected String[] text;

    public PingCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public void execute(CommandData command) {
        super.execute(command);
        bot.send("Pong " + String.join(" ", text) + (fuck ? "FUCK YOURSELF" : ""));
    }

    @Override
    public String getName() {
        return "Ping";
    }
}
