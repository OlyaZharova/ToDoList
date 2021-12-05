package todolist.service;

import todolist.repository.Status;
import todolist.repository.Task;

import java.util.Collection;

public interface TaskService {

    int addTask(
            String[] parameters
    );

    void closeTask(
            int id
    );

    void deleteTask(
            int id
    );

    Collection<Task> getAllTasksByStatus(Status status);

}
