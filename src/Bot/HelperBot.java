package Bot;

import Command.CommandData;

import java.lang.reflect.InvocationTargetException;

public interface HelperBot {
    public void execute(CommandData command);
    public void send(String message);
}
