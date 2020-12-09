package main.java.com.urfu.Devy.bot.telegram;

import main.java.com.urfu.Devy.formatter.TextFormatter;

public class TelegramTextFormatter extends TextFormatter {
    @Override
    public TextFormatter bold(String text) {
        builder.append("*").append(text).append("*");
        return this;
    }

    @Override
    public TextFormatter cursive(String text) {
        builder.append("_").append(text).append("_");
        return this;
    }

    @Override
    public TextFormatter underline(String text) {
        builder.append("*").append(text).append("*");
        return this;
    }

    @Override
    public TextFormatter code(String text) {
        builder.append("```").append(text).append("```");
        return this;
    }

    @Override
    public TextFormatter quotation(String text) {
        builder.append("`").append(text).append("`");
        return this;
    }
}
