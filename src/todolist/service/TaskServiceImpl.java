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
        if (title.isBlank()) {
            throw new IllegalArgumentException("Title should not be null");
        }
        return repository.addTask(new Task(title, Status.OPEN));
    }

    @Override
    public String closeTask(int id) {
        Task task = repository.getTask(id);
        if (task != null) {
            task.setStatus(Status.CLOSED);
            if (repository.saveTask(task)) {
                return null;
            } else {
                return "Задача не была закрыта";
            }
        } else {
            return "Задача с заданным id не найденна";
        }

    }

    @Override
    public String deleteTask(int id) {
        Task task = repository.getTask(id);
        if (task != null) {
            if (repository.deleteTask(id)) {
                return null;
            } else {
                return "Задача не была удалена";
            }
        } else {
            return "Задача с заданным id не найденна";
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
