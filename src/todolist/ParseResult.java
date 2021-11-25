package todolist;

import java.util.function.Consumer;

public sealed interface ParseResult extends Result<ParseResult.Success, ParseResult.Error, ParseResult> {
  
  @Override
  default ParseResult ifSuccess(Consumer<Success> successConsumer) {
    if (this instanceof Success success) {
      successConsumer.accept(success);
    }
    return this;
  }
  
  @Override
  default ParseResult ifError(Consumer<Error> errorConsumer) {
    if (this instanceof Error error) {
      errorConsumer.accept(error);
    }
    return this;
  }
  
  record Success(Command command, String[] parameters) implements ParseResult {}
  
  record Error(String error) implements ParseResult {}
  
}
