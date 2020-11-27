package main.java.com.urfu.Devy.group.modules;

import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.database.RepositoryController;
import main.java.com.urfu.Devy.github.RepositoryInfo;

public class GroupGithub extends GroupModule {

    public GroupGithub(int groupId) {
        super(groupId);
    }

    public GroupGithub() {
        this(0);
    }

    public void addRepository(String name, String repository) throws CommandException {
        if (!RepositoryController.getGitHubRepository().addRepository(groupId, new RepositoryInfo(name, repository)))
            throw new CommandException("Repository was already added.");
    }

    public void removeRepository() throws CommandException{
        if(!RepositoryController.getGitHubRepository().removeRepository(groupId))
            throw new CommandException("No such repository");
    }

    public Boolean hasRepository(){
        return RepositoryController.getGitHubRepository().hasRepository(groupId);
    }

    public String getLastCommitDate(){
        return RepositoryController.getGitHubRepository().getLastCommitDate(groupId);
    }
}
