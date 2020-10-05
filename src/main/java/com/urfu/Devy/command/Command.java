package main.java.com.urfu.Devy.command;

import com.beust.jcommander.JCommander;
import main.java.com.urfu.Devy.bot.HelperBot;

public abstract class Command {
    protected HelperBot bot;
    public abstract String execute(CommandData command);

    public Command(HelperBot bot){
        this.bot = bot;
    }

    public void parseArgs(CommandData command){
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(command.getArgs());
    }
}
