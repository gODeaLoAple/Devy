package main.java.com.urfu.Devy.command.commands;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandName;
import org.apache.log4j.Logger;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

@CommandName(name = "showBoard")
public class ShowBoardCommand extends Command {
    private static final Logger log = Logger.getLogger(AddBoardCommand.class);
    @Parameter()
    private List<String> name = new ArrayList<>();

    public ShowBoardCommand(HelperBot bot){
        super(bot);
    }

    @Override
    public String execute(CommandData command) {
        parseArgs(command);
        if(name.isEmpty())
            return "Enter board name";
        if(name.size() > 1)
            return "too mach args";
        try {
            return bot.getGroup().getBoard(name.get(0)).show();
        } catch (KeyException e) {
            log.error(e);
            return "no such board";
        }
    }
}
