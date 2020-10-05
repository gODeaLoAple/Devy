package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.command.CommandsController;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
            properties.load(new FileInputStream(PATH_TO_TOKEN));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
