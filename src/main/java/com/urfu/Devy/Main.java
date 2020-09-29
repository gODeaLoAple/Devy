package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Command.CommandsController;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var bot = new DiscordBot("nope");
        CommandsController.constructCommandsDictionary();
        var exampleGroup = new GroupInfo(1);
        bot.addGroup(exampleGroup);
        bot.receive(exampleGroup, "$ping");

        bot.receive(exampleGroup, "$help");
        Scanner in = new Scanner(System.in);
        while(true){
            var command = in.nextLine();
            if(command == null || command.isEmpty())
                continue;
            bot.receive(exampleGroup, command);
        }
    }
}
