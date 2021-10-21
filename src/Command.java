public interface Command {

    void addTask(String title);

    void closeTask(int id);

    void deleteTask(int id);

    void list(String status);
}
