package todolist;

public class ToDoList {

    public static void main(String[] args) {
        Ui ui = new ConsoleUI(System.in, System.out);
        TaskRepository repository = new TaskRepositoryImpl(ui);
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    repository.flush();
                }));
        TaskService service = new TaskServiceImpl(repository);
        CommandParser parser = new CommandParserImpl();
        InputValidation validation = new InputValidationImpl();
        ConsoleController controller = new ConsoleControllerImpl(service, parser, ui, validation);
        controller.show();
    }
}
