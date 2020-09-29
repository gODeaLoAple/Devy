package main.java.com.urfu.Devy.Command.Commands;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Bot.HelperBot;
import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.Command.Command;
import main.java.com.urfu.Devy.Command.CommandData;
import main.java.com.urfu.Devy.Command.CommandName;

@CommandName(name = "help")
public class HelpCommand extends Command {

    public HelpCommand(HelperBot bot) {
        super(bot);
    }

    @Parameter(description = "enter command name to get info about it")
    private String targetCommand;

    @Override
    public String execute(CommandData command) {
        parseArgs(command);
        var result = new StringBuilder();
        if(targetCommand == null || targetCommand.isEmpty()){
            for(var a : DiscordBot.getInstance().getAllCommands()){
                var info = a.info();
                result.append(a.name());
                if(!info.isEmpty())
                    result.append(" :: ").append(info);
                result.append("\n");
            }
            return result.toString();
        }
        //info about all commands
        var commandClass = DiscordBot.getInstance().getCommand(targetCommand);
        var commandAnnotation = commandClass.getDeclaredAnnotation(CommandName.class);
        //return commandAnnotation.name();
        return commandAnnotation.name() + (commandAnnotation.info().isEmpty() ? "" : ": " + commandAnnotation.info());
    }
}
