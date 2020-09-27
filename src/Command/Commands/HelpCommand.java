package Command.Commands;
import Bot.HelperBot;
import Command.*;
import com.beust.jcommander.Parameter;

public class HelpCommand extends Command{

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
    public void execute(CommandData command) {
        super.execute(command);
        if(targetCommand.isEmpty()){
            //info about concrete command. mb reflection
            bot.send("help me");
            return;
        }
        //info about all commands
        bot.send("help");
    }
}
