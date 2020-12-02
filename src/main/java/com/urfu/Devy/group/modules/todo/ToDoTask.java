package main.java.com.urfu.Devy.group.modules.todo;

public class ToDoTask {
    private final int id;
    private final String name;
    private final String author;
    private final String executor;
    private final String text;

    public ToDoTask(int id, String name, String author, String executor, String text) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.executor = executor;
        this.text = text;
    }

    public ToDoTask(String name, String author, String executor, String text) {
        this(0, name, author, executor, text);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getExecutor() {
        return executor;
    }

    public String getInfo(){
        return "**Name**: *%s*\n**Author**: *%s*\n**Executor**: *%s*\n**Task**: *%s*"
                .formatted(name, author, executor, text);
    }
}
