package Command.Commands;

import Bot.HelperBot;
import Command.*;

public class UnknownCommand extends Command{
    public UnknownCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public String execute(CommandData command) {
        return "Unknown command. Try again!";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getInfo() {
        return null;
    }
}
