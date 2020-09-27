package Bot;

import Command.CommandData;

public interface HelperBot {
    public void execute(CommandData command);
    public void send(String message);
}
