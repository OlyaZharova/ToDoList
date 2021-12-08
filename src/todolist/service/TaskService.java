package todolist.service;

import todolist.repository.Status;
import todolist.repository.Task;

import java.util.Collection;

public interface TaskService {

    int addTask(String[] parameters);

    String closeTask(int id);

    String deleteTask(int id);

    Collection<Task> getAllTasksByStatus(Status status);

}
