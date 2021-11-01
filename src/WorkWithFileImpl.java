import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class WorkWithFileImpl implements WorkWithFile {

    private static Path PATH;
    static  {
        try {
            var dir = Paths.get(System.getProperty("user.home")+"/.todolist");
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
    private File file;
    private int id;

    private static class SingletonHelper {
        private static final WorkWithFileImpl INSTANCE = new WorkWithFileImpl();
    }

    public static WorkWithFileImpl getInstance() {
        return WorkWithFileImpl.SingletonHelper.INSTANCE;
    }

    public WorkWithFileImpl() {
        file = new File(PATH.toAbsolutePath().toString());
        id = 0;
    }

    @Override
    public HashMap<Integer, Task> readFile() {
        HashMap<Integer, Task> toDoList = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(" ");
                id = Integer.parseInt(lines[0]);
                String stutus = lines[lines.length - 1];
                String title = line.substring(String.valueOf(id).length(), line.length() - stutus.length()).trim();
                Task task = new Task(title, stutus);
                toDoList.put(id, task);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        id++;
        return toDoList;
    }

    @Override
    public void addToFile(Task task, int id) {
        String taskStr = task.getTitle() + " " + task.getStatus();
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter(file, true));
            writter.write(id + " " + taskStr + "\n");
            writter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromFile(int id) {
        String[] lines = getLinesFromFile();
        lines[foundStringNumber(id)] = "";
        writeLinesToFile(lines);
    }

    @Override
    public void editInFile(Task task, int id) {
        String[] lines = getLinesFromFile();
        lines[foundStringNumber(id)] = id + " " + task.getTitle() + " " + task.getStatus();
        writeLinesToFile(lines);
    }

    @Override
    public int getId() {
        return id;
    }

    private int foundStringNumber(int id) {
        int number = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int lineId = Integer.parseInt(line.split(" ")[0]);
                if (lineId == id) {
                    break;
                }
                number++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    private String[] getLinesFromFile() {
        String[] lines = new String[countLinesInFile()];
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                lines[i] = line;
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private int countLinesInFile() {
        int number = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) {
                number++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    private void writeLinesToFile(String[] lines) {
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter(file));
            for (String line : lines) {
                if (!line.equals("")) {
                    writter.write(line + "\n");
                }
            }
            writter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
