package todolist;

public class TaskServiceImpl implements TaskService {
    private TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addTask(String[] parameters) {
        String title = String.join(" ", parameters);
        Task task = new Task(title, Status.OPEN);
        repository.addTask(task);
    }

    @Override
    public void listTask(Status status) {
        Status statusList;
        if (status == null) {
            statusList = Status.OPEN;
        } else {
            statusList = status;
        }
        repository.listTask(statusList);
    }

    @Override
    public void closeTask(int id) {
        Task task = repository.getTask(id);
        if (task != null) {
            task.setStatus(Status.CLOSED);
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
