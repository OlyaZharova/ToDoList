package todolist.repository;

public class Task {
    private Integer id;
    private final String title;
    private Status status;

    public Task(
            String title,
            Status status
    ) {
        this(title, status, null);
    }

    public Task(
            String title,
            Status status,
            Integer id
    ) {
        this.title = title;
        this.status = status;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;

        Task task = (Task) o;

        if (id != null ? !id.equals(task.id) : task.id != null)
            return false;
        if (title != null ? !title.equals(task.title) : task.title != null)
            return false;
        return status != null ? status.equals(task.status) : task.status == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title='" + title + '\'' + ", status='" + status + '\'' + '}';
    }

}