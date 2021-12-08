package todolist.controller;

import todolist.UI.UI;
import todolist.service.TaskService;
import todolist.util.CommandParser;
import todolist.util.InputValidation;

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
                        case ADD -> {
                            try {
                                ui.showToUser(String.valueOf(service.addTask(result.parameters())));
                            } catch (IllegalArgumentException exception) {
                                ui.showToUser(exception.getMessage());
                            }
                        }
                        case LIST -> {
                            inputValidation.parseStatus(result.parameters())
                                    .ifSuccess(valResult -> {
                                        service.getAllTasksByStatus(valResult.status())
                                                .forEach(it -> ui.showToUser(it.toString()));
                                    })
                                    .ifError(error -> ui.showToUser(error.error()));
                        }
                        case CLOSE -> {
                            inputValidation.parseInteger(result.parameters())
                                    .ifSuccessInt(valResult -> {
                                        String resultOfClose = service.closeTask(valResult.id());
                                        if (resultOfClose != null) {
                                            ui.showToUser(resultOfClose);
                                        }
                                    })
                                    .ifError(error -> ui.showToUser(error.error()));
                        }
                        case DELETE -> {
                            inputValidation.parseInteger(result.parameters())
                                    .ifSuccessInt(valResult -> {
                                        String resultOfDelete = service.deleteTask(valResult.id());
                                        if (resultOfDelete != null) {
                                            ui.showToUser(resultOfDelete);
                                        }
                                    })
                                    .ifError(error -> ui.showToUser(error.error()));
                        }
                        case EXIT -> {
                            ui.showToUser("Bye bye!");
                            System.exit(0);
                        }
                    }
                })
                .ifError(error -> ui.showToUser(error.error()));
        show();
    }

}
