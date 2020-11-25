package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.todo.ToDo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ToDoRepository extends Repository {

    private final Logger log = LogManager.getLogger("ToDoRepository");

    public boolean addToDoList(int groupId, ToDo todo) {
        try (var statement = database.getConnection().createStatement()) {
            if (hasToDoWithName(groupId, todo.getName()))
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `todo` (`name`, `groupId`)
                    VALUES ('%s', %d)
                    """.formatted(todo.getName(), groupId)
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addToDoList'", throwables);
            return false;
        }
    }

    public boolean removeToDoList(int groupId, String toDoId) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    DELETE FROM `todo` 
                    WHERE `name`='%s' AND `groupId`=%d
                    """.formatted(toDoId, groupId)) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeToDoList'", throwables);
            return false;
        }
    }

    public ToDo getToDoByName(int groupId, String todoName) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `idkey`, `name`
                    FROM `todo`
                    WHERE `name`='%s' AND `groupId`=%d
                    """.formatted(todoName, groupId));
            if (!data.next())
                return null;
            return new ToDo(data.getInt("idkey"), data.getString("name"));
        } catch (SQLException throwables) {
            log.error("On 'getToDoByName'", throwables);
            return null;
        }
    }

    public Collection<ToDo> getAllToDo(int groupId) {
        var result = new ArrayList<ToDo>();
        try  (var statement = database.getConnection().createStatement()){
            var data = statement.executeQuery("""
                SELECT `idkey`, `name`
                FROM `todo`
                WHERE `groupId`=%d;
                """.formatted(groupId));
            while(data.next()) {
                result.add(new ToDo(data.getInt("idkey"), data.getString("name")));
            }
        } catch (SQLException throwables) {
            log.error("On 'getAllToDo'", throwables);
        }
        return result;
    }

    public boolean hasToDoWithName(int groupId, String todoName) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `todo`
                        WHERE `groupId`=%d AND `name`='%s')
                    """.formatted(groupId, todoName)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasToDoWithName'", throwables);
            return false;
        }
    }

}
