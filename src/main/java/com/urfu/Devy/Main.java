package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.command.CommandsController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static String PATH_TO_TOKEN = "src/config.properties";
    public static void main(String[] args) {
        var token = loadProperties(PATH_TO_TOKEN).getProperty("token");
        var bot = new DiscordBot(token);
        CommandsController.constructCommandsDictionary();
        bot.start();
    }

    private static Properties loadProperties(String path) {
        var properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
