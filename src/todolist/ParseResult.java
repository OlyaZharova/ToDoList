package todolist;

import java.util.function.Consumer;

public sealed interface ParseResult {
  
  default ParseResult ifSuccess(Consumer<Success> consumer) {
    return this;
  }
  
  default ParseResult ifError(Consumer<String> consumer) {
    return this;
  }
  
  record Success(Command command, String[] parameters) implements ParseResult {
  
  }
  
  record Error(String error) implements ParseResult {
  
  }
  
}
