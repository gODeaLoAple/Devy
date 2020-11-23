package main.java.com.urfu.Devy.command;

import com.beust.jcommander.JCommander;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

public abstract class Command {

    protected final GroupInfo groupInfo;
    protected final MessageSender sender;
    protected final String[] args;

    public Command(GroupInfo groupInfo, MessageSender sender, @NotNull String[] args) {
        this.groupInfo = groupInfo;
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

    public String getName() {
        if (getClass().isAnnotationPresent(CommandName.class))
            return getClass().getAnnotation(CommandName.class).name();
        return "";
    };

    public String extractParametersInfo() {
        var sb = new StringBuilder();
        var jc = JCommander
            .newBuilder()
            .addObject(this)
            .build();
        jc.setProgramName("%s".formatted(getName()));
        jc.getUsageFormatter().usage(sb);
        return sb.toString();
    }

}
