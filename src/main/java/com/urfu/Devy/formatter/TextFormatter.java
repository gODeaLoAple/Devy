package main.java.com.urfu.Devy.formatter;

public abstract class TextFormatter {

    protected StringBuilder builder;

    public TextFormatter() {
        builder = new StringBuilder();
    }

    public abstract TextFormatter bold(String text);

    public abstract TextFormatter cursive(String text);

    public abstract TextFormatter underline(String text);

    public abstract TextFormatter code(String text);

    public abstract  TextFormatter quotation(String text);

    public ICombineFunction combine(ICombineFunction...functions) {
        if (functions.length == 0)
            throw new IllegalArgumentException();
        if (functions.length == 1)
            return functions[0];
        return text -> {
            var result = text;
            for (var i = functions.length - 1; i > 0; i--)
                result = functions[i].apply(result).get();
            return this;
        };
    }

    public TextFormatter raw(String text) {
        builder.append(text);
        return this;
    }



    public TextFormatter line() {
        builder.append(System.lineSeparator());
        return this;
    }

    public String get() {
        return builder.toString();
    }

    protected interface ICombineFunction {
        TextFormatter apply(String text);
    }

}