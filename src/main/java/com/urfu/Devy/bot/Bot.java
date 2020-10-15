package main.java.com.urfu.Devy.bot;


import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;

public interface Bot {
    void handleMessage(String groupId, String senderId, String message) throws ParseCommandException, CommandException;
    void addGroup(GroupInfo group);
    void removeGroup(GroupInfo group);
}