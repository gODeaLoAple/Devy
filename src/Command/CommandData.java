package Command;

public class CommandData {
    private final String name;
    private final String line;

    public CommandData(String name, String line) {
        this.name = name;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }
}
