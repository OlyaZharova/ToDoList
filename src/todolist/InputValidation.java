package todolist;

public interface InputValidation {
    ValidationResult parseInteger(String[] parameters);

    ValidationResult parseStatus(String[] parameters);
}
