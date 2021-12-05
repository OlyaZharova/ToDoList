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

public class ToDoList {

    public static void main(String[] args) {
        UI ui = new ConsoleUI(System.in, System.out);
        TaskRepository repository = new FileBasedTaskRepository();
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    repository.flush();
                    ui.closeScanner();
                }));
        TaskService service = new TaskServiceImpl(repository);
        CommandParser parser = new CommandParserImpl();
        InputValidation validation = new InputValidationImpl();
        ConsoleController controller = new ConsoleControllerImpl(service, parser, ui, validation);
        controller.show();
    }
}
