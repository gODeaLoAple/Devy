package main.java.com.urfu.Devy.database.repositories.implemented;

import main.java.com.urfu.Devy.database.repositories.Repository;
import main.java.com.urfu.Devy.todo.ToDoTask;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public class ToDoTaskRepository extends Repository {
    private final Logger log = Logger.getLogger("ToDoTaskRepository");

    public boolean addTask(int todoId, ToDoTask task) {
        try (var statement = database.getConnection().createStatement()) {
            if (hasTaskWithName(todoId, task.getName()))
                return false;
            return statement.executeUpdate("""
                    INSERT INTO `tasks` (`name`, `author`, `executor`, `content`, `todoId`) 
                    VALUES ('%s','%s','%s','%s',%d)
                    """.formatted(
                    task.getName(),
                    task.getAuthor(),
                    task.getExecutor(),
                    task.getText(),
                    todoId
            )) > 0;
        } catch (SQLException throwables) {
            log.error("On 'addTask'", throwables);
            return false;
        }
    }

    public boolean removeTaskByName(int todoId, String taskName) {
        try (var statement = database.getConnection().createStatement()) {
            return statement.executeUpdate("""
                    DELETE FROM `tasks`
                    WHERE `todoId`=%d AND `name`='%s'
                    """.formatted(todoId, taskName)
            ) > 0;
        } catch (SQLException throwables) {
            log.error("On 'removeTaskByName'", throwables);
            return false;
        }
    }

    public ArrayList<ToDoTask> getAllTasksInToDo(int toDoId) {
        var result = new ArrayList<ToDoTask>();
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `name`, `author`, `executor`, `content` 
                    FROM `tasks` 
                    WHERE `todoId`=%d
                    """.formatted(toDoId));
            while (data.next())
                result.add(new ToDoTask(
                        data.getString("name"),
                        data.getString("author"),
                        data.getString("executor"),
                        data.getString("content")));
        } catch (SQLException throwables) {
            log.error("On 'getTaskByName'", throwables);
        }
        return result;
    }

    public ToDoTask getTaskByName(int todoId, String taskName) {
        try (var statement = database.getConnection().createStatement()) {
            var data = statement.executeQuery("""
                    SELECT `idkey`, `author`, `executor`, `content` 
                    FROM `tasks` 
                    WHERE `todoId`=%d AND `name`='%s'
                    """.formatted(todoId, taskName));
            if (data.next())
                return new ToDoTask(
                        data.getInt("idkey"),
                        taskName,
                        data.getString("author"),
                        data.getString("executor"),
                        data.getString("content"));
        } catch (SQLException throwables) {
            log.error("On 'getTaskByName'", throwables);
        }
        return null;
    }

    public boolean hasTaskWithName(int todoId, String taskName) {
        try (var statement = database.getConnection().createStatement()) {
            var result = statement.executeQuery("""
                    SELECT EXISTS(
                        SELECT `idkey` 
                        FROM `tasks`
                        WHERE `todoId`=%d AND `name`='%s')
                    """.formatted(todoId, taskName)
            );
            return result.next() && result.getInt(1) > 0;
        } catch (SQLException throwables) {
            log.error("On 'hasTaskWithId'", throwables);
            return false;
        }
    }
}
