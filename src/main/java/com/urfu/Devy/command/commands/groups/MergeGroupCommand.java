package main.java.com.urfu.Devy.command.commands.groups;

import com.beust.jcommander.Parameter;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandName;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.group.modules.chats.Chats;
import main.java.com.urfu.Devy.group.modules.todo.GroupTodo;
import main.java.com.urfu.Devy.group.modules.todo.ToDo;
import main.java.com.urfu.Devy.sender.MessageSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@CommandName(
        name="merge",
        info="Merge groups from different platforms into one"
)
public class MergeGroupCommand extends Command {

    @Parameter(names="-d", description = "Merge discord group")
    public boolean discord;

    @Parameter(names="-t", description = "Merge telegram group")
    public boolean telegram;

    @Parameter(description = "[groupId]")
    public List<String> text;

    public MergeGroupCommand(GroupInfo group, MessageSender sender, @NotNull String[] args) {
        super(group, sender, args);
    }

    @Override
    public void execute() {
        try {
            validate();
            var id = getChatId();
            var merger = getMerger(id);
            merger.merge();
            sender.send("Groups merged!");
        } catch (CommandException e) {
            sender.send(e.getMessage());
        }

    }

    private GroupMerger getMerger(String id) {
        if (telegram)
            return new TelegramMerger(groupInfo, Long.valueOf(id));
        if (discord)
            return new DiscordMerger(groupInfo, id);
        throw new IllegalArgumentException("Merger not found");
    }

    private String getChatId() {
        return text.get(0);
    }

    protected void validate() throws CommandException {
        if (text == null || text.size() < 1)
            throw new CommandException("Incorrect count of arguments.");
        if (!telegram && !discord)
            throw new CommandException("Option not found.");
        if (telegram && discord)
            throw new CommandException("Use only one platform.");
        if (telegram && !isLong(getChatId()))
                throw new CommandException("Incorrect chat id.");
    }

    private boolean isLong(String str) {
        if (str == null)
            return false;
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
abstract class GroupMerger {

    protected final GroupInfo group;

    public GroupMerger(GroupInfo info) {
        group = info;
    }

    public void merge() throws CommandException {
        var otherGroup = getOtherGroup();
        var chats = group.asChats().getChats();

        validateChats(chats);
        validateTodo(group.asTodo().getAllToDo(), otherGroup.asTodo().getAllToDo());

        mergeChats(chats);
        mergeTodo(otherGroup);

        removeOtherGroup(otherGroup);
    }

    protected abstract void validateChats(Chats chats) throws CommandException;
    protected abstract void mergeChats(Chats chats);

    private void validateTodo(Collection<ToDo> todo, Collection<ToDo> otherTodo) throws CommandException {
        var otherTodoNames = otherTodo
                .stream()
                .map(ToDo::getName)
                .collect(Collectors.toSet());
        var common = todo
                .stream()
                .map(ToDo::getName)
                .distinct()
                .filter(otherTodoNames::contains)
                .collect(Collectors.toSet());
        if (common.size() > 0)
            throwCommonTodoException(common);
    }

    private void throwCommonTodoException(Set<String> common) throws CommandException {
        var sb = new StringBuilder();
        sb.append("Cannot merge todo. Rename or delete these todo:").append(System.lineSeparator());
        for (var todo : common)
            sb.append("- ").append(todo).append(System.lineSeparator());
        throw new CommandException(sb.toString());
    }

    private void mergeTodo(GroupInfo other) {
        RepositoryController.getTodoRepository().updateGroupId(group.getId(), other.getId());
    }

    protected void removeOtherGroup(GroupInfo group) {
        group.remove();
    }


    protected GroupInfo getOtherGroup() throws CommandException {
        try {
            return RepositoryController.getGroupRepository().getGroupById(getOtherGroupId());
        } catch (NoSuchElementException e) {
            throw new CommandException("Group not found");
        }
    }

    protected abstract int getOtherGroupId();
}

class TelegramMerger extends GroupMerger {
    protected final Long id;
    TelegramMerger(GroupInfo info, Long id) {
        super(info);
        this.id = id;

    }

    @Override
    protected void validateChats(Chats chats) throws CommandException {
        if (chats.hasTelegram())
            throw new CommandException("You cannot merge on the same platform.");
    }

    @Override
    protected void mergeChats(Chats chats) {
        chats.setTelegramId(id);
    }

    @Override
    protected int getOtherGroupId() {
        return RepositoryController.getChatsRepository().getGroupIdByTelegramId(id);
    }

}

class DiscordMerger extends GroupMerger {

    protected final String id;
    public DiscordMerger(GroupInfo info, String id) {
        super(info);
        this.id = id;
    }

    @Override
    protected void validateChats(Chats chats) throws CommandException {
        if (chats.hasDiscord())
            throw new CommandException("You cannot merge on the same platform.");
    }

    @Override
    protected void mergeChats(Chats chats) {
        chats.setDiscordId(id);
    }

    @Override
    protected int getOtherGroupId() {
        return RepositoryController.getChatsRepository().getGroupIdByDiscordChatId(id);
    }
}
