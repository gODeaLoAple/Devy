package main.java.com.urfu.Devy.bot.discord;

import main.java.com.urfu.Devy.formatter.TextFormatter;

public class DiscordTextFormatter extends TextFormatter {
    @Override
    public TextFormatter bold(String text) {
        builder.append("**").append(text).append("**");
        return this;
    }

    @Override
    public TextFormatter cursive(String text) {
        builder.append("*").append(text).append("*");
        return this;
    }

    @Override
    public TextFormatter underline(String text) {
        builder.append("__").append(text).append("__");
        return this;
    }

    @Override
    public TextFormatter code(String text) {
        builder.append("```").append(System.lineSeparator()).append(text).append("```");
        return this;
    }

    @Override
    public TextFormatter quotation(String text) {
        builder.append("`").append(text).append("`");
        return this;
    }
}
