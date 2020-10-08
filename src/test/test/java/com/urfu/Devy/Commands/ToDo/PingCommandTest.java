package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.bot.GroupInfo;
import main.java.com.urfu.Devy.bot.MessageSender;
import main.java.com.urfu.Devy.command.commands.PingCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PingCommandTest {

    private PingCommand command;
    private MessageSender sender;

    @BeforeEach
    public void SetUp() {

    }

    public PingCommandTest(){}

    @Test
    public void testEmpty(){
        var command = createCommandAndExecute(new String[] {});
        Assertions.assertEquals("Pong ", command.getSender().getId());
    }

    @Test
    public void testWithExtraArgs(){
        var command = createCommandAndExecute(new String[] {"asd", "qwe", "eqw"});
        Assertions.assertEquals("Pong asd qwe eqw", command.getSender().getId());
    }

    @Test
    public void testWIthFlag(){
        var command = createCommandAndExecute(new String[] {"-f"});
        Assertions.assertEquals("Pong Hello", command.getSender().getId());
    }

    @Test
    public void test2(){

    }

    private PingCommand createCommandAndExecute(String[] args){
        var command = new PingCommand(new GroupInfo("1"), new EmptySender(), args);
        command.execute();
        return command;
    }

}
