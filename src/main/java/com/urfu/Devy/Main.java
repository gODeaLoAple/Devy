package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Command.CommandsController;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String PATH_TO_PROPERTIES = "src/config.properties";
    public static void main(String[] args) {
        try {
            var fileInputSystem = new FileInputStream(PATH_TO_PROPERTIES);
            var prop = new Properties();
            prop.load(fileInputSystem);
            var token = prop.getProperty("token", "wrong key");

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
