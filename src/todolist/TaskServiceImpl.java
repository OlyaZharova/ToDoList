package todolist;

import java.util.Locale;

public class TaskServiceImpl implements TaskService {
    private TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addTask(String[] parameters) {
        String title = String.join(" ", parameters);
        String status = "open";
        Task task = new Task(title, status);
        repository.addTask(task);
    }

    @Override
    public void listTask(Status status) {
        String statusList = status.toString().toLowerCase(Locale.ROOT);
        repository.listTask(statusList);
    }

    @Override
    public void closeTask(int id) {
        Task task = repository.getTask(id);
        if (task != null) {
            task.setStatus("closed");
            repository.closeTask(new Task(task.getTitle(), task.getStatus(), id));
        }

    }

    @Override
    public void deleteTask(int id) {
        Task task = repository.getTask(id);
        if (task != null) {
            repository.deleteTask(id);
        }
    }
}
