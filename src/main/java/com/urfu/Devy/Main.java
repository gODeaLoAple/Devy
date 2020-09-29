package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var bot = DiscordBot.getInstance();
        var exampleGroup = new GroupInfo(1);
        bot.addGroup(exampleGroup);
        bot.receive(exampleGroup, "$ping");

        bot.receive(exampleGroup, "$ping -f who fuck?");
        //bot.receive(exampleGroup, "$help");
        Scanner in = new Scanner(System.in);
        while(true){
            var command = in.nextLine();
            if(command == null || command.isEmpty())
                continue;
            bot.receive(exampleGroup, command);
        }
    }
}
