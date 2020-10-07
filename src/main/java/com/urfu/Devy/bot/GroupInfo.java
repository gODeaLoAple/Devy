package main.java.com.urfu.Devy.bot;
import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandsController;

import java.util.HashMap;
import java.util.Map;

public class GroupInfo {
    protected final String id;
    protected final HashMap<String, MessageSender> senders;
    protected final Map<String, ToDo> toDoLists;

    public GroupInfo(String id) {
        this.id = id;
        senders = new HashMap<>();
        toDoLists = new HashMap<>();
    }

    public void addSender(MessageSender sender) {
        var senderId = sender.getId();
        if (senders.containsKey(senderId))
            throw new IllegalArgumentException("The channel had already been added.");
        senders.put(sender.getId(), sender);
    }

    public void removeSender(MessageSender sender) {
        var senderId = sender.getId();
        if (!senders.containsKey(senderId))
            throw new IllegalArgumentException("The channel was not found.");
        senders.remove(senderId);
    }

    public void addToDo(ToDo toDo) {
        if (toDoLists.containsKey(toDo.getId()))
            throw new IllegalArgumentException("The ToDo list was already added.");
        toDoLists.put(toDo.getId(), toDo);
    }

    public void removeToDo(ToDo toDo) {
        removeToDo(toDo.getId());
    }

    public void removeToDo(String toDoId) {
        if (!toDoLists.containsKey(toDoId))
            throw new IllegalArgumentException("The ToDo list %s was not founded.".formatted(toDoId));
        toDoLists.remove(toDoId);
    }

    public ToDo getToDo(String toDoId) {
        if (!toDoLists.containsKey(toDoId))
            throw new IllegalArgumentException("The ToDo list \"%s\" was not founded.".formatted(toDoId));
        return toDoLists.get(toDoId);
    }

    public String getId() {
        return id;
    }

    public void execute(CommandData data, String senderId) {
        CommandsController
                .createCommand(this, senders.get(senderId), data)
                .execute();
    }

    public void sendMessage(String message, String senderId) {
        if (!senders.containsKey(senderId))
            throw new IllegalArgumentException("The channel was not founded.");
        senders.get(senderId).send(message);
    }

}