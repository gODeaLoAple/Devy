package main.java.com.urfu.Devy.groups;
import jdk.jshell.spi.ExecutionControl;
import main.java.com.urfu.Devy.bot.HelperBot;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.bot.discord.DiscordMessageSender;
import main.java.com.urfu.Devy.command.CommandData;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;

public class GroupInfo {

    protected final HelperBot bot;
    protected final String id;
    protected final HashMap<String, MessageSender> senders;

    public GroupInfo(String id) {
        this.id = id;
        bot = new HelperBot(this);
        senders = new HashMap<>();
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