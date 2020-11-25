package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.bot.telegram.TelegramBot;
import main.java.com.urfu.Devy.command.CommandsController;

import main.java.com.urfu.Devy.database.DataBase;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.database.repositories.implemented.GroupRepository;
import main.java.com.urfu.Devy.database.repositories.implemented.ToDoRepository;
import main.java.com.urfu.Devy.database.repositories.implemented.ToDoTaskRepository;
import main.java.com.urfu.Devy.database.repositories.mocked.MockGroupRepository;
import main.java.com.urfu.Devy.database.repositories.mocked.MockToDoRepository;
import main.java.com.urfu.Devy.database.repositories.mocked.MockToDoTaskRepository;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private static final String PATH_TO_TOKENS = "src/config.properties";
    private static final String PATH_TO_DATABASE = "src/database.properties";

    public static void main(String[] args) {
        try {
            initRepositories();
            CommandsController.constructCommandsDictionary();
            createBots(loadProperties(Main.PATH_TO_TOKENS));
        }
        catch (Exception e) {
            log.error(e);
        }
    }

    private static void createBots(Properties config) {
        var bots = new Bot[] {
                new DiscordBot(config.getProperty("discord_token")),
                new TelegramBot(config.getProperty("telegram_token")),
        };
        for (var bot : bots)
            bot.start();
    }

    private static DataBase createDataBase(Properties config) throws Exception {
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

        try {
            Repository.setDatabase(createDataBase(loadProperties(Main.PATH_TO_DATABASE)));
            initImplementedRepositories();
        } catch (Exception e) {
            log.error("Exception when initRepositories: " + e.getMessage());
            initMockedRepositories();
        }

    }

    private static void initImplementedRepositories() {
        RepositoryController.setTodoRepository(new ToDoRepository());
        RepositoryController.setToDoTaskRepository(new ToDoTaskRepository());
        RepositoryController.setGroupRepository(new GroupRepository());
    }

    private static void initMockedRepositories() {
        RepositoryController.setTodoRepository(new MockToDoRepository());
        RepositoryController.setToDoTaskRepository(new MockToDoTaskRepository());
        RepositoryController.setGroupRepository(new MockGroupRepository());
    }
}
