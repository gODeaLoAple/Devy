package Command;
import Bot.HelperBot;
import com.beust.jcommander.JCommander;

public abstract class Command {
    protected HelperBot bot;
    public void execute(CommandData command){ //для логгирования, например
        parseArgs(command);
    };

    public Command(Bot.HelperBot bot){
        this.bot = bot;
    }

    public void parseArgs(CommandData command){
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(command.getArgs());
    }
}
