package main.java.com.urfu.Devy.command.commands;


import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;

public class UnknownCommand extends Command {
    public UnknownCommand(HelperBot bot) {
        super(bot);
    }

    @Override
    public String execute(CommandData command) {
        return "Unknown command. Try again!";
    }

}
