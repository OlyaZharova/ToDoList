package todolist;

public interface TaskService {

    void addTask(
            String[] parameters
    );

    void listTask(
            Status status
    );

    void closeTask(
            int id
    );

    void deleteTask(
            int id
    );

}
