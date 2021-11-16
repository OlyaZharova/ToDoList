package todolist;

import java.util.Arrays;
import java.util.Locale;

public class CommandParserImpl implements CommandParser {

    @Override
    public ParseResult parse(String source) {
        String[] sourses = source.trim().split(" ");
        String commandStr = sourses[0].toUpperCase(Locale.ROOT);
        try {
            Command command = Command.valueOf(commandStr);
            String[] parameters = Arrays.copyOfRange(sourses, 1, sourses.length);
            ParseResult.Success success = new ParseResult.Success(command, parameters);
            return success;
        } catch (IllegalArgumentException e) {
            ParseResult.Error error = new ParseResult.Error("Метод не найден");
            return error;
        } catch (NullPointerException e) {
            ParseResult.Error error = new ParseResult.Error("Ничего не введено");
            return error;
        }
    }
}
