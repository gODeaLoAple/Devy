package Command;
import Bot.HelperBot;
import com.beust.jcommander.JCommander;

public abstract class Command {
    protected HelperBot bot;
    public abstract String execute(CommandData command);
    public abstract String getName();
    public abstract String getInfo();

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
