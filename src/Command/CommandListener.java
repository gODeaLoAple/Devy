package Command;

import Bot.HelperBot;
import com.beust.jcommander.Parameter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandListener {
    //public Map<String, Command> commands = new HashMap<>();
    @CommandAnnotation(name = "ping")
    public class Ping extends Command{

        @Parameter(names = {"-f, --fuck"}, description = "don't press f")
        private boolean fuck;

        @Parameter(description = "some text if you want")
        private String[] text;

        public Ping(HelperBot bot) {
            super(bot);
        }

        @Override
        public void execute(CommandData command) {
            super.execute(command);
            var result = new StringBuilder("Pong ");
            if (text != null && text.length != 0)
                result.append(String.join(" ", text));
            if (fuck)
                result.append("FUCK YOURSELF");
            bot.send(result.toString());
        }
    }

    public void test(){
        var a = CommandListener.class.getClasses();
    }
}
