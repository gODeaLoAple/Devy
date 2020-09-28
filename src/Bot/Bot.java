package Bot;

public interface Bot {
    void send(String message);
    void receive(GroupInfo from, String message);
    void addGroup(GroupInfo group);
    void removeGroup(GroupInfo group);
}