package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.sender.MessageSender;
import net.dv8tion.jda.api.entities.User;

public class DiscordUserSender implements MessageSender {

    private User user;
    public DiscordUserSender(User user) {
        this.user = user;
    }

    @Override
    public void send(String message) {
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }

    @Override
    public String getId() {
        return user.getId();
    }

}
