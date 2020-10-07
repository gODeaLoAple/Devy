package main.java.com.urfu.Devy.ToDo;

public class ToDoTask {
    private final String id;
    private final String author;
    private final String executor;
    private final String text;

    public ToDoTask(String id, String author, String executor, String text) {
        this.id = id;
        this.author = author;
        this.executor = executor;
        this.text = text;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString(){
        return "name: %s\nauthor: %s\nexecutor: %s\ntask: %s".formatted(id, author, executor, text);
    }
}
