package main.java.com.urfu.Devy.Command;

import com.beust.jcommander.JCommander;
import main.java.com.urfu.Devy.Bot.HelperBot;

public abstract class Command {
    protected HelperBot bot;
    public abstract String execute(CommandData command);
    public abstract String getName();
    public abstract String getInfo();

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
