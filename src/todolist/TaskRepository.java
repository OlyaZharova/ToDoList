package todolist;

import java.util.HashMap;

public interface TaskRepository {

    HashMap<Integer, Task> readFile();

    void flush();

    void addTask(Task task);

    Task getTask(int id);

    void closeTask(Task task);

    void deleteTask(int task);

    void listTask(Status status);

}
