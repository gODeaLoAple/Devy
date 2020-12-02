package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.bot.Bot;
import main.java.com.urfu.Devy.bot.discord.DiscordBot;
import main.java.com.urfu.Devy.bot.discord.DiscordBotBuilder;
import main.java.com.urfu.Devy.bot.telegram.TelegramBot;
import main.java.com.urfu.Devy.bot.telegram.TelegramBotBuilder;
import main.java.com.urfu.Devy.command.CommandsController;

import main.java.com.urfu.Devy.database.DataBase;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.database.repositories.implemented.*;
import main.java.com.urfu.Devy.database.repositories.Repository;

import main.java.com.urfu.Devy.database.repositories.mocked.*;
import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getSimpleName());

    public static final String PATH_TO_TOKENS = "src/config.properties";
    public static final String PATH_TO_DATABASE = "src/database.properties";

    private static DiscordBot discordBot;
    private static TelegramBot telegramBot;

    public static void main(String[] args) {
        try {
            log.info("Configuring commands...");
            CommandsController.constructCommandsDictionary();
            log.info("Commands configuring successfully!");

            log.info("Configuring database...");
            var database = createDataBase(loadProperties(Main.PATH_TO_DATABASE));
            Repository.setDatabase(database);
            log.info("Database created!");

            log.info("Configuring repositories...");
            initRepositories();
            log.info("Repositories created!");


            var tokens = loadProperties(Main.PATH_TO_TOKENS);
            log.info("Configuring github...");
            initGithub(tokens);
            log.info("Github authorized!");

            log.info("Configuring bots...");
            initBots(tokens);
            log.info("Bots created!");

        }
        catch (Exception e) {
            log.error(e);
        }
    }

    private static void initBots(Properties config) {
        discordBot = new DiscordBot(config.getProperty("discord_token"));
        discordBot.start(new DiscordBotBuilder());

        telegramBot = new TelegramBot(config.getProperty("telegram_token"));
        telegramBot.start(new TelegramBotBuilder());
    }

    private static void initGithub(Properties config) {
        new GitHubClient().setOAuth2Token(config.getProperty("githubToken"));
    }

    private static DataBase createDataBase(Properties config) throws Exception {
        return new DataBase(config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password"));
    }

    public static Properties loadProperties(String path) {
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
        RepositoryController.setGitHubRepository(new GitHubRepository());
        RepositoryController.setChatsRepository(new ChatsRepository());
    }

    private static void initMockedRepositories() {
        RepositoryController.setTodoRepository(new MockToDoRepository());
        RepositoryController.setToDoTaskRepository(new MockToDoTaskRepository());
        RepositoryController.setGroupRepository(new MockGroupRepository());
        RepositoryController.setGitHubRepository(new MockGithubRepository());
        RepositoryController.setChatsRepository(new MockChatsRepository());
    }
}
