package main.java.com.urfu.Devy.group.modules;

public abstract class GroupModule {

    protected int groupId;

    public GroupModule(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int id) {
        groupId = groupId;
    };
}
