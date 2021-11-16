package todolist;

import java.util.function.Consumer;

public sealed interface ValidationResult {

    default ValidationResult ifSuccessInt(Consumer<SuccessInteger> consumer, ValidationResult success) {
        consumer.accept((SuccessInteger) success);
        return success;
    }

    default ValidationResult ifSuccess(Consumer<SuccessStatus> consumer, ValidationResult success) {
        consumer.accept((SuccessStatus) success);
        return success;
    }

    default void ifError(Consumer<ValidationResult.Error> consumer, ValidationResult error) {
        consumer.accept((ValidationResult.Error) error);
    }

    record SuccessInteger(int id) implements ValidationResult {
    }

    record SuccessStatus(Status status) implements ValidationResult {
    }

    record Error(String error) implements ValidationResult {
    }
}
