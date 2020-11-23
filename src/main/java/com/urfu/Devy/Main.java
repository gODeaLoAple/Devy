package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.command.CommandsController;

import main.java.com.urfu.Devy.database.DataBase;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.GitHubRepository;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.database.repositories.ToDoRepository;
import main.java.com.urfu.Devy.database.repositories.ToDoTaskRepository;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private static final String PATH_TO_TOKEN = "src/config.properties";
    private static final String PATH_TO_DATABASE = "src/database.properties";
    public static void main(String[] args) {
        try {
            var database = createDataBase();
            var bot = createBot();
            Repository.setDatabase(database);
            initRepositories();
            CommandsController.constructCommandsDictionary();
            bot.start();
        }
        catch (Exception e) {
            log.error(e);
        }
    }

    private static DiscordBot createBot() {
        var config = loadProperties(Main.PATH_TO_TOKEN);
        return new DiscordBot(config.getProperty("token"));
    }

    private static DataBase createDataBase() throws Exception {
        var config = loadProperties(Main.PATH_TO_DATABASE);
        return new DataBase(config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password"));
    }

    private static Properties loadProperties(String path) {
        var properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            log.error("Config file not found", e);
        }
        return properties;
    }

    private static void initRepositories() {
        RepositoryController.setTodoRepository(new ToDoRepository());
        RepositoryController.setToDoTaskRepository(new ToDoTaskRepository());
        RepositoryController.setGitHubRepository(new GitHubRepository());
    }
}
