package Command;

import Bot.HelperBot;

public class UnknownCommand extends Command{
    public UnknownCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public void execute(CommandData command) {
        bot.send("Unknown command. Try again!");
    }

    @Override
    public String getName() {
        return "";
    }
}