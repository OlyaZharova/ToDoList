package todolist.service;

import todolist.repository.Status;
import todolist.repository.Task;
import todolist.repository.TaskRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public int addTask(String[] parameters) {
        String title = String.join(" ", parameters);
        Task task = new Task(title, Status.OPEN, repository.getId());
        return repository.addTask(task);
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

    @Override
    public Collection<Task> getAllTasksByStatus(Status status) {
        if (status == null) {
            status = Status.OPEN;
        }
        if (status == Status.ALL) {
            return repository.getAll();
        } else {
            Status finalStatus = status;
            return repository.getAll()
                    .stream()
                    .filter(it -> it.getStatus() == finalStatus)
                    .collect(Collectors.toUnmodifiableSet());
        }

    }

}
