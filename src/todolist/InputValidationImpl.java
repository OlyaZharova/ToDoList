package todolist;

import java.util.Locale;

public class InputValidationImpl implements InputValidation {
    @Override
    public ValidationResult parseInteger(String[] parameters) {
        if (parameters != null || parameters.length != 0) {
            String patameter = parameters[0];
            try {
                int id = Integer.parseInt(patameter);
                return new ValidationResult.SuccessInteger(id);
            } catch (NumberFormatException e) {
                return new ValidationResult.Error("Неверный id");
            }
        } else return new ValidationResult.Error("Aргумент отсутствует");
    }

    @Override
    public ValidationResult parseStatus(String[] parameters) {
        if (parameters != null && parameters.length != 0) {
            String parameter = parameters[0];
            try {
                Status status = Status.valueOf(parameter.toUpperCase(Locale.ROOT));
                return new ValidationResult.SuccessStatus(status);
            } catch (IllegalArgumentException e) {
                return new ValidationResult.Error("Некорректный статус");
            }
        } else {
            return new ValidationResult.SuccessStatus(Status.OPEN);
        }
    }
}
