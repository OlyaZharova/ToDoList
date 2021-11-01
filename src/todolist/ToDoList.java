package todolist;

public class ToDoList {
  
  public static void main(String[] args) {
    TaskRepository repository = new TaskRepositoryImpl();
    Runtime.getRuntime()
           .addShutdownHook(new Thread(() -> {
             System.out.println("EXIT");
           }));
    Ui ui = new ConsoleUI(System.in, System.out);
    TaskService service = new TaskServiceImpl(repository);
    ConsoleController controller = new ConsoleControllerImpl(service, System.console(), parser, ui);
    controller.show();
  }
  
}
