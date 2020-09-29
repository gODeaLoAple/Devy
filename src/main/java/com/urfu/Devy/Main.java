package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Goups.GroupInfo;

public class Main {

    public static void main(String[] args) {
        var bot = DiscordBot.getInstance();
        var exampleGroup = new GroupInfo(1);
        bot.addGroup(exampleGroup);
        bot.receive(exampleGroup, "$lljk");
    }
}
