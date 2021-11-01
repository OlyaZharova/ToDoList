import java.util.HashMap;

public interface WorkWithFile {

    HashMap<Integer, Task> readFile();

    void addToFile(Task task, int id);

    void deleteFromFile(int id);

    void editInFile(Task task, int id);

    int getId();
}
