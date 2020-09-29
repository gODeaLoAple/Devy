package main.java.com.urfu.Devy.Command.Commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.Bot.HelperBot;
import main.java.com.urfu.Devy.Command.Command;
import main.java.com.urfu.Devy.Command.CommandData;

public class HelpCommand extends Command {

    public HelpCommand(HelperBot bot) {
        super(bot);
    }

    @Parameter(description = "enter command name to get info about it")
    private String targetCommand;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getInfo() {
        return "help about help =)?";
    }

    @Override
    public String execute(CommandData command) {
        if(targetCommand.isEmpty()){
            //info about concrete command. mb reflection
            return "help me";
        }
        //info about all commands
        return "help";
    }
}
