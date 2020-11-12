package main.java.com.urfu.Devy.database.repositories;

import main.java.com.urfu.Devy.todo.ToDo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ToDoRepository extends Repository {

    private final Logger log = Logger.getLogger("ToDoRepository");

    public boolean addToDoList(String groupId, ToDo todo) {
        try (var statement = database.getConnection().createStatement()) {
            if (hasToDoWithName(groupId, todo.getName()))
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `todo` (`name`, `groupId`)
                    VALUES ('%s','%s')
                    """.formatted(todo.getName(), groupId)
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addToDoList'", throwables);
            return false;
        }
    }

    public boolean removeToDoList(String groupId, String toDoId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    DELETE FROM `todo` 
                    WHERE `name`='%s' AND `groupId`='%s'
                    """.formatted(toDoId, groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeToDoList'", throwables);
            return false;
        }
    }

    public ToDo getToDoByName(String groupId, String todoName) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `idkey`, `name`
                    FROM `todo`
                    WHERE `name`='%s' AND `groupId`='%s'
                    """.formatted(todoName, groupId));
            if (!data.next())
                return null;
            return new ToDo(data.getInt("idkey"), data.getString("name"));
        } catch (SQLException throwables) {
            log.error("On 'getToDoByName'", throwables);
            return null;
        }
    }

    public Collection<ToDo> getAllToDo(String groupId) {
        var result = new ArrayList<ToDo>();
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT `idkey`, `name`
                FROM `todo`
                WHERE `groupId`='%s';
                """.formatted(groupId));
            while(data.next()) {
                result.add(new ToDo(data.getInt("idkey"), data.getString("name")));
            }
        } catch (SQLException throwables) {
            log.error("On 'getAllToDo'", throwables);
        }
        return result;
    }

    public boolean hasToDoWithName(String groupId, String todoName) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `todo`
                        WHERE `groupId`='%s' AND `name`='%s')
                    """.formatted(groupId, todoName)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasToDoWithName'", throwables);
            return false;
        }
    }

}
