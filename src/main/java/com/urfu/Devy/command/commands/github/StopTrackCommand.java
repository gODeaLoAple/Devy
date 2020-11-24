package main.java.com.urfu.Devy.command.commands.github;

import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import org.jetbrains.annotations.NotNull;

@CommandName(name = "stoptrack")
public class StopTrackCommand extends Command {

    public StopTrackCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute(){
        try {
            group.stopTrack();
            sender.send("tracking removed");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }
    }
}
