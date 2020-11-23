package test.java.com.urfu.Devy.commands;

import main.java.com.urfu.Devy.sender.EmptySender;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.commands.common.PingCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PingCommandTest {
    private EmptySender sender;

    @BeforeEach
    public void SetUp() {
        sender = new EmptySender();
    }

    public PingCommandTest(){}

    @Test
    public void testEmpty(){
        createCommandAndExecute(new String[] {});
        sender.assertMessage("Pong");
    }

    @Test
    public void testWithExtraArgs(){
        createCommandAndExecute(new String[] {"asd", "qwe", "eqw"});
        sender.assertMessage("Pong asd qwe eqw");
    }

    @Test
    public void testWIthFlag(){
        createCommandAndExecute(new String[] {"-f"});
        sender.assertMessage("Pong Hello");
    }

    private void createCommandAndExecute(String[] args){
        var command = new PingCommand(new GroupInfo(1), sender, args);
        command.execute();
    }

}
