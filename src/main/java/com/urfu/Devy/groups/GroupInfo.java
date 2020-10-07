package main.java.com.urfu.Devy.groups;
import jdk.jshell.spi.ExecutionControl;
import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.bot.discord.DiscordMessageSender;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.todo.ToDoBoard;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.security.KeyException;
import java.util.HashMap;

public class GroupInfo {

    protected final HelperBot bot;
    protected final String id;
    protected final HashMap<String, MessageSender> senders;
    protected final HashMap<String, ToDoBoard> boards;

    public GroupInfo(String id) {
        this.id = id;
        bot = new HelperBot(this);
        senders = new HashMap<>();
        boards = new HashMap<>();
    }

    public void addSender(MessageSender sender) {
        var senderId = sender.getId();
        if (senders.containsKey(senderId))
            throw new IllegalArgumentException("Канал уже был добавлен");
        senders.put(sender.getId(), sender);
    }

    public void removeSender(MessageSender sender) {
        var senderId = sender.getId();
        if (!senders.containsKey(senderId))
            throw new IllegalArgumentException("Канал не найден");
        senders.remove(senderId);
    }

    public void removeToDoBoard(String boardName) throws KeyException {
        if(!boards.containsKey(boardName))
            throw new KeyException("no board with this name" + boardName);
        boards.remove(boardName);
    }
    public void removeToDoBoard(ToDoBoard board) throws KeyException {
        removeToDoBoard(board.getName());
    }

    public ToDoBoard getBoard(String boardName) throws KeyException{
        if(!boards.containsKey(boardName))
            throw new KeyException("no board with this name" + boardName);
        return boards.get(boardName);
    }

    public Boolean hasToDoBoard(String boardName){
        return boards.containsKey(boardName);
    }

    public void addToDoBoard(ToDoBoard board){
        boards.put(board.getName(), board);
    }

    public String getId() {
        return id;
    }

    public void execute(CommandData data, String senderId) {
        var response = bot.execute(data);
        sendMessage(response, senderId);
    }

    public void sendMessage(String message, String senderId) {
        if (!senders.containsKey(senderId))
            throw new IllegalArgumentException("Канал не найден");
        senders.get(senderId).send(message);
    }

}