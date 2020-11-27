package main.java.com.urfu.Devy.bot;

public interface BotBuilder {
    void build(Bot bot, String token) throws BotBuildException;
}

