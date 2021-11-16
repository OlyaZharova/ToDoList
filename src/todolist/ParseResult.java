package todolist;

import java.util.function.Consumer;

public sealed interface ParseResult {

    default void ifSuccess(Consumer<Success> consumer, ParseResult success) {
        consumer.accept((Success) success);
    }

    default void ifError(Consumer<Error> consumer, ParseResult error) {
        consumer.accept((Error) error);

    }

    record Success(Command command, String[] parameters) implements ParseResult {
    }

    record Error(String error) implements ParseResult {
    }

}
