package main.java.com.urfu.Devy.github;

public class RepositoryInfo {
    private String name;
    private String repository;

    public RepositoryInfo(String name, String repository){
        this.name = name;
        this.repository = repository;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepositoryName() {
        return repository;
    }

    public void setRepositoryName(String repository) {
        this.repository = repository;
    }
}
