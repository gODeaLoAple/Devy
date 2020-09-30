package main.java.com.urfu.Devy;

import main.java.com.urfu.Devy.Bot.Discord.DiscordBot;
import main.java.com.urfu.Devy.Command.CommandsController;
import main.java.com.urfu.Devy.Goups.GroupInfo;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main extends ListenerAdapter {
    public static String PATH_TO_TOKEN = "src/config.properties";
    public static void main(String[] args) {
        //try {

            var prop = new Properties();
        try {
            prop.load(new FileInputStream(PATH_TO_TOKEN));
        } catch (IOException e) {
            e.printStackTrace();
        }
        var token = prop.getProperty("token");
            System.out.println(token);
            //var c = JDABuilder.createDefault(token).addEventListeners(new Main()).build();
            //c.awaitReady();

            //a.awaitReady();
            var bot = new DiscordBot(token);
            CommandsController.constructCommandsDictionary();
            bot.start();
            /*var exampleGroup = new GroupInfo(1);
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
        } catch (IOException | LoginException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("$ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }
    }
}
