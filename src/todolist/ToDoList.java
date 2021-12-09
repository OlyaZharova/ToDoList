package todolist;

import todolist.UI.ConsoleUI;
import todolist.UI.UI;
import todolist.controller.ConsoleController;
import todolist.controller.ConsoleControllerImpl;
import todolist.repository.FileBasedTaskRepository;
import todolist.repository.TaskRepository;
import todolist.service.TaskService;
import todolist.service.TaskServiceImpl;
import todolist.util.CommandParser;
import todolist.util.CommandParserImpl;
import todolist.util.InputValidation;
import todolist.util.InputValidationImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ToDoList {
    private static Path PATH;
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

    public static void main(String[] args) {
        UI ui = new ConsoleUI(System.in, System.out);
        TaskRepository repository = new FileBasedTaskRepository(new File(PATH.toAbsolutePath().toString()));
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    repository.flush();
                    ui.close();
                }));
        TaskService service = new TaskServiceImpl(repository);
        CommandParser parser = new CommandParserImpl();
        InputValidation validation = new InputValidationImpl();
        ConsoleController controller = new ConsoleControllerImpl(service, parser, ui, validation);
        controller.show();
    }
}
