package com.company;

import Bot.Discord.DiscordBot;
import Goups.GroupInfo;

public class Main {

    public static void main(String[] args) {
        var bot = DiscordBot.getInstance();
        var exampleGroup = new GroupInfo(1);
        bot.addGroup(exampleGroup);
        bot.receive(exampleGroup, "$lljk");
    }
}
