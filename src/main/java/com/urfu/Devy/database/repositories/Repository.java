package main.java.com.urfu.Devy.database.repositories;

import main.java.com.urfu.Devy.database.DataBase;

public abstract class Repository {
    protected static DataBase database;

    public static void setDatabase(DataBase database) {
        Repository.database = database;
    }

}
