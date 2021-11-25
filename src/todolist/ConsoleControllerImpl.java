package todolist;

public class ConsoleControllerImpl implements ConsoleController {
  
  private final TaskService service;
  private final UI ui;
  private final CommandParser parser;
  private final InputValidation inputValidation;
  
  public ConsoleControllerImpl(
      TaskService service,
      CommandParser parser,
      UI ui,
      InputValidation inputValidation
  ) {
    this.service = service;
    this.parser = parser;
    this.ui = ui;
    this.inputValidation = inputValidation;
  }
  
  @Override
  public void show() {
    parser.parse(ui.getUserInput())
          .ifSuccess(result -> {
            switch (result.command()) {
              case ADD -> service.addTask(result.parameters());
              case LIST -> {
                ValidationResult validationResult = inputValidation.parseStatus(result.parameters());
                if (validationResult instanceof ValidationResult.SuccessStatus) {
                  validationResult.ifSuccess(valResult -> {
                    service.listTask(valResult.status());
                  }, validationResult);
                } else if (validationResult instanceof ValidationResult.Error) {
                  validationResult.ifError(error -> {
                    ui.showToUser(error.error());
                  }, validationResult);
                }
              }
              case CLOSE -> {
                ValidationResult validationResult = inputValidation.parseInteger(result.parameters());
                if (validationResult instanceof ValidationResult.SuccessInteger) {
                  validationResult.ifSuccessInt(valResult -> {
                    service.closeTask(valResult.id());
                  }, validationResult);
                } else if (validationResult instanceof ValidationResult.Error) {
                  validationResult.ifError(error -> {
                    ui.showToUser(error.error());
                  }, validationResult);
                }
              }
              case DELETE -> {
                ValidationResult validationResult = inputValidation.parseInteger(result.parameters());
                if (validationResult instanceof ValidationResult.SuccessInteger) {
                  validationResult.ifSuccessInt(valResult -> {
                    service.deleteTask(valResult.id());
                  }, validationResult);
                } else if (validationResult instanceof ValidationResult.Error) {
                  validationResult.ifError(error -> {
                    ui.showToUser(error.error());
                  }, validationResult);
                }
              }
              case EXIT -> {
                System.out.println("Bye bye!");
                System.exit(0);
              }
            }
          })
          .ifError(error -> ui.showToUser(error.error()));
    show();
  }
  
}
