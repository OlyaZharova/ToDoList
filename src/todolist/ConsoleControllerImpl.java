package todolist;

import java.io.Console;

public class ConsoleControllerImpl implements ConsoleController {
  
  private final TaskService service;
  private final Console console;
  private final CommandParser parser;
  
  public ConsoleControllerImpl(
      TaskService service,
      Console console,
      CommandParser parser
  ) {
    this.service = service;
    this.console = console;
    this.parser = parser;
  }
  
  @Override
  public void show() {
    var line = console.readLine();
    parser.parse(line)
          .ifSuccess(result -> {
            switch (result.command()) {
              case ADD -> {
                service.addTask(result.command(), result.parameters());
              }
              case LIST -> {}
              case CLOSE -> {}
              case DELETE -> {}
//              case EXIT -> {
//                return;
//              }
              default -> {
          
              }
            }
          })
          .ifError(error -> {
                     console.writer()
                            .write(error);
                   }
    
          );
    show();
  }
  
}
