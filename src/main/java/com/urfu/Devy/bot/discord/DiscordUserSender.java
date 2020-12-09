package main.java.com.urfu.Devy.bot.discord;

import net.dv8tion.jda.api.entities.User;

import java.util.Objects;

public class DiscordUserSender extends DiscordSender {

    private final User user;
    public DiscordUserSender(User user) {
        this.user = user;
    }

    @Override
    public void send(String message) {
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }

    @Override
    public String getId(){
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscordUserSender that = (DiscordUserSender) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

}
