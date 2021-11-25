package todolist;

import java.util.function.Consumer;

public sealed interface Result<Success, Error, THIS extends Result<Success, Error, THIS>> permits ParseResult {
  
  THIS ifSuccess(Consumer<Success> successConsumer);
  
  THIS ifError(Consumer<Error> errorConsumer);
  
}
