package todolist.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class FileBasedTaskRepository implements TaskRepository {

    private static Path PATH;
    private File file;
    private HashMap<Integer, Task> toDoList;
    private int id;

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

    public FileBasedTaskRepository() {
        this.file = new File(PATH.toAbsolutePath().toString());
        toDoList = readFile();
        id = getId(toDoList);
    }


    private HashMap<Integer, Task> readFile() {
        HashMap<Integer, Task> toDoList = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(" ");
                int id = Integer.parseInt(lines[0]);
                Status status = Status.valueOf(lines[lines.length - 1].toUpperCase(Locale.ROOT));
                String title = line.substring(String.valueOf(id).length(), line.length() - status.toString().length()).trim();
                toDoList.put(id, new Task(title, status, id));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toDoList;
    }

    @Override
    public Collection<Task> getAll() {
        return toDoList.values();
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
    public Integer getId() {
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
    public int addTask(Task task) {
        id++;
        toDoList.put(id, task);
        return id;
    }

    @Override
    public Task getTask(int id) {
        return toDoList.get(id);
    }

    @Override
    public void closeTask(Task task) {
        toDoList.get(task.getId()).setStatus(task.getStatus());
    }

    @Override
    public void deleteTask(int id) {
        toDoList.remove(id);
    }

}
