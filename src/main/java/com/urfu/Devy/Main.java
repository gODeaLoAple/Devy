package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.command.CommandsController;

<<<<<<< HEAD
import org.apache.log4j.Logger;

=======
>>>>>>> 5e7acf172e757702a8a374f84a0714a1b3cc433c
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private static final String PATH_TO_TOKEN = "src/config.properties";
    public static void main(String[] args) {
<<<<<<< HEAD
        try {
            var token = loadProperties(PATH_TO_TOKEN).getProperty("token");
            var bot = new DiscordBot(token);
            CommandsController.constructCommandsDictionary();
            bot.start();
        }
        catch (IllegalArgumentException e){
            log.error("wrong token", e);
        }
=======
        var token = loadProperties(PATH_TO_TOKEN).getProperty("token");
        var bot = new DiscordBot(token);
        CommandsController.constructCommandsDictionary();
        bot.start();
>>>>>>> 5e7acf172e757702a8a374f84a0714a1b3cc433c
    }

    private static Properties loadProperties(String path) {
        var properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            log.error("config file not found", e);
        }
        return properties;
    }
}
