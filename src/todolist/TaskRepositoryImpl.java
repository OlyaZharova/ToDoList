package todolist;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TaskRepositoryImpl implements TaskRepository {

    private static Path PATH;
    private File file;
    private HashMap<Integer, Task> toDoList;
    private int id;
    private final Ui ui;

    static {
        try {
            var dir = Paths.get(System.getProperty("user.home") + "/.todolist");
            Files.createDirectories(dir);
            PATH = dir.resolve("todolist.data");
            if (!Files.exists(PATH)) {
                Files.createFile(PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public TaskRepositoryImpl(Ui ui) {
        file = new File(PATH.toAbsolutePath().toString());
        toDoList = readFile();
        id = getId(toDoList);
        this.ui = ui;
    }


    @Override
    public HashMap<Integer, Task> readFile() {
        HashMap<Integer, Task> toDoList = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(" ");
                int id = Integer.parseInt(lines[0]);
                String stutus = lines[lines.length - 1];
                String title = line.substring(String.valueOf(id).length(), line.length() - stutus.length()).trim();
                toDoList.put(id, new Task(title, stutus));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toDoList;
    }

    private Integer getId(HashMap<Integer, Task> toDoList) {
        int id = 1;
        if (!toDoList.isEmpty()) {
            Iterator iterator = toDoList.keySet().iterator();
            while (iterator.hasNext()) {
                id = Integer.parseInt(iterator.next().toString());
            }
        }
        return id;
    }

    @Override
    public void flush() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Integer key : toDoList.keySet()) {
                writer.write(key + " " + toDoList.get(key).getTitle() + " " + toDoList.get(key).getStatus() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTask(Task task) {
        id++;
        toDoList.put(id, task);
        ui.getLine(String.valueOf(id));

    }

    @Override
    public Task getTask(int id) {
        Task task = toDoList.get(id);
        return task;
    }

    @Override
    public void closeTask(Task task) {
        toDoList.get(task.getId()).setStatus(task.getStatus());
    }

    @Override
    public void deleteTask(int id) {
        toDoList.remove(id);
    }

    @Override
    public void listTask(String status) {
        Iterator<Map.Entry<Integer, Task>> iterator = toDoList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Task> taskEntry = iterator.next();
            if (taskEntry.getValue().getStatus().equals(status) || status.equals("all")) {
                ui.getLine(taskEntry.getKey() + " | " + taskEntry.getValue().getTitle() + " | " + taskEntry.getValue().getStatus());
            }
        }
    }
}