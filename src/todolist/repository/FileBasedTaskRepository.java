package todolist.repository;

import java.io.*;
import java.util.*;

public class FileBasedTaskRepository implements TaskRepository {

    private File file;
    private Map<Integer, Task> toDoList;
    private int id;

    public FileBasedTaskRepository(File file) {
        this.file = file;
        this.toDoList = deserialize(file);
        this.id = Optional
                .ofNullable(findMaxId(toDoList))
                .orElse(1);
    }

    private static HashMap<Integer, Task> deserialize(File source) {
        HashMap<Integer, Task> toDoList = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(source));
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

    private Integer findMaxId(Map<Integer, Task> toDoList) {
        return Collections.max(toDoList.keySet());
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
        task.setId(id);
        toDoList.put(id, task);
        return id;
    }

    @Override
    public Task getTask(int id) {
        return toDoList.get(id);
    }

    @Override
    public boolean saveTask(Task task) {
        return toDoList.replace(task.getId(), task) != null;
    }

    @Override
    public boolean deleteTask(int id) {
        return toDoList.remove(id) != null;
    }

}
