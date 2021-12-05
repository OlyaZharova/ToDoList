package todolist.util;

import todolist.repository.Status;

import java.util.function.Consumer;

public sealed interface ValidationResult extends Validation<ValidationResult.SuccessStatus, ValidationResult.SuccessInteger, ValidationResult.Error, ValidationResult> {

    @Override
    default ValidationResult ifSuccessInt(Consumer<SuccessInteger> successIntConsumer) {
        if (this instanceof SuccessInteger successInteger) {
            successIntConsumer.accept(successInteger);
        }
        return this;
    }

    @Override
    default ValidationResult ifSuccess(Consumer<SuccessStatus> successStatusConsumer) {
        if (this instanceof SuccessStatus successStatus) {
            successStatusConsumer.accept(successStatus);
        }
        return this;
    }

    @Override
    default ValidationResult ifError(Consumer<ValidationResult.Error> errorConsumer) {
        if (this instanceof ValidationResult.Error error) {
            errorConsumer.accept(error);
        }
        return this;
    }

    record SuccessInteger(int id) implements ValidationResult {
    }

    record SuccessStatus(Status status) implements ValidationResult {
    }

    record Error(String error) implements ValidationResult {
    }
}
