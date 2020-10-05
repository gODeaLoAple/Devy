package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Command.CommandsController;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static String PATH_TO_TOKEN = "config.properties";

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        var prop = new Properties();
        try {
            prop.load(new FileInputStream(PATH_TO_TOKEN));
        } catch (IOException e) {
            log.error("config file not found", e);
        }

        try{
            var token = prop.getProperty("token");
            var bot = new DiscordBot(token);
            CommandsController.constructCommandsDictionary();
            bot.start();
        }
        catch (IllegalArgumentException e){
            log.error("wrong token", e);
        }

    }

}
