import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommandImpl implements Command {

    private int id;
    private HashMap<Integer, Task> toDoList;
    private WorkWithFile workWithFile = WorkWithFileImpl.getInstance();

    public CommandImpl() {
        toDoList = workWithFile.readFile();
        id = workWithFile.getId();
    }


    @Override
    public void addTask(String title) {
        Task task = new Task(title, "open");
        System.out.println(id);
        toDoList.put(id, task);
        workWithFile.addToFile(task, id);
        id++;
    }

    @Override
    public void closeTask(int id) {
        Task task = toDoList.get(id);
        if (task != null) {
            task.setStatus("closed");
            workWithFile.editInFile(task, id);
        } else {
            System.out.println("Task don't exist");
        }

    }

    @Override
    public void deleteTask(int id) {
        Task task = toDoList.get(id);
        if (task != null) {
            toDoList.remove(id);
            workWithFile.deleteFromFile(id);
        } else {
            System.out.println("Task don't exist");
        }
    }

    @Override
    public void list(String status) {
        Iterator<Map.Entry<Integer, Task>> iterator = toDoList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Task> taskEntry = iterator.next();
            if (taskEntry.getValue().getStatus().equals(status) || status.equals("all")) {
                System.out.println(taskEntry.getKey() + " | " + taskEntry.getValue().getTitle() + " | " + taskEntry.getValue().getStatus());
            }
        }
    }
}
