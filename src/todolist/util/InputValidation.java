package todolist.util;

public interface InputValidation {
    ValidationResult parseInteger(String[] parameters);

    ValidationResult parseStatus(String[] parameters);
}
