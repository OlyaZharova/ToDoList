package todolist;

import java.util.Collection;

public interface TaskService {

    void addTask(
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
