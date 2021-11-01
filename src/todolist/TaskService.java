package todolist;

public interface TaskService {
  
  void addTask(
      Command command,
      String[] parameters
  );
  
}
