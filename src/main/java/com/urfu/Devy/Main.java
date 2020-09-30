package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Command.CommandsController;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String PATH_TO_TOKEN = "src/config.properties";
    public static void main(String[] args) {
        try {
            var prop = new Properties();
            prop.load(new FileInputStream(PATH_TO_TOKEN));
            var token = prop.getProperty("token");

            var bot = new DiscordBot(token);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
