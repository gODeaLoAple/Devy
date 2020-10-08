package main.java.com.urfu.Devy.command;

import com.beust.jcommander.JCommander;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import org.jetbrains.annotations.NotNull;

public abstract class Command {

    protected final GroupInfo group;
    protected final MessageSender sender;
    protected final String[] args;

    public Command(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        this.group = group;
        this.sender = sender;
        this.args = args;
        parseArgs();
    }

    private void parseArgs() {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);
    }

    public abstract void execute();

    public boolean isNull() { return false; }

    public String[] getArgs() {
        return args.clone();
    }

    public MessageSender getSender() {
        return sender;
    }
}
