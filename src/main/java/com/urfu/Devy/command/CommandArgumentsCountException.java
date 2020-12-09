package main.java.com.urfu.Devy.command;

public class CommandArgumentsCountException extends CommandException {

    public CommandArgumentsCountException(String message) {
        super("Incorrect count of arguments. " + message);
    }

    public CommandArgumentsCountException(int expected) {
        this("Expected " + expected);
    }
}
