package todolist;

public class ConsoleControllerImpl implements ConsoleController {

    private final TaskService service;
    private final Ui ui;
    private final CommandParser parser;
    private final InputValidation inputValidation;

    public ConsoleControllerImpl(
            TaskService service,
            CommandParser parser,
            Ui ui,
            InputValidation inputValidation) {
        this.service = service;
        this.parser = parser;
        this.ui = ui;
        this.inputValidation = inputValidation;
    }

    @Override
    public void show() {
        var line = ui.readLine();
        ParseResult results = parser.parse(line);
        if (results instanceof ParseResult.Success) {
            results.ifSuccess(result -> {
                switch (result.command()) {
                    case ADD -> {
                        service.addTask(result.parameters());
                        show();
                    }
                    case LIST -> {
                        ValidationResult validationResult = inputValidation.parseStatus(result.parameters());
                        if (validationResult instanceof ValidationResult.SuccessStatus) {
                            validationResult.ifSuccess(valResult -> {
                                service.listTask(valResult.status());
                            }, validationResult);
                        } else if (validationResult instanceof ValidationResult.Error) {
                            validationResult.ifError(error -> {
                                ui.getLine(error.error());
                            }, validationResult);
                        }
                        show();
                    }
                    case CLOSE -> {
                        ValidationResult validationResult = inputValidation.parseInteger(result.parameters());
                        if (validationResult instanceof ValidationResult.SuccessInteger) {
                            validationResult.ifSuccessInt(valResult -> {
                                service.closeTask(valResult.id());
                            }, validationResult);
                        } else if (validationResult instanceof ValidationResult.Error) {
                            validationResult.ifError(error -> {
                                ui.getLine(error.error());
                            }, validationResult);
                        }
                        show();
                    }
                    case DELETE -> {
                        ValidationResult validationResult = inputValidation.parseInteger(result.parameters());
                        if (validationResult instanceof ValidationResult.SuccessInteger) {
                            validationResult.ifSuccessInt(valResult -> {
                                service.deleteTask(valResult.id());
                            }, validationResult);
                        } else if (validationResult instanceof ValidationResult.Error) {
                            validationResult.ifError(error -> {
                                ui.getLine(error.error());
                            }, validationResult);
                        }
                        show();
                    }
                    case EXIT -> {
                        return;
                    }
                }
            }, results);
        } else if (results instanceof ParseResult.Error) {
            results.ifError(error -> {
                ui.getLine(error.error());
            }, results);
            show();
        }
    }

}
