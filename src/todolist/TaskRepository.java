package todolist;

import java.util.Collection;

public interface TaskRepository {
  
  Collection<Task> getAll();
  
  void flush();
  
  void addTask(Task task);
  
  Task getTask(int id);
  
  void closeTask(Task task);
  
  void deleteTask(int task);
  
  
}
