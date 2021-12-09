package todolist.util;

import java.util.function.Consumer;

public sealed interface Validation<SuccessStatus, SuccessInteger, Error, THIS extends Validation<SuccessStatus, SuccessInteger, Error, THIS>> permits ValidationResult {

    THIS ifSuccessInt(Consumer<SuccessInteger> successConsumer);

    THIS ifSuccess(Consumer<SuccessStatus> successConsumer);

    THIS ifError(Consumer<Error> errorConsumer);

}
