package main.java.com.urfu.Devy.Command.Commands;


import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Command;
import main.java.com.urfu.Devy.Command.CommandData;

public class UnknownCommand extends Command {
    public UnknownCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public String execute(CommandData command) {
        return "Unknown command. Try again!";
    }

}
