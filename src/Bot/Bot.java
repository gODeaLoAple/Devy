package Bot;

public interface Bot {
    void Send(String message);
    void Receive(GroupInfo from, String message);
    void AddGroup(GroupInfo group);
    void RemoveGroup(GroupInfo group);
}