package todolist.util;

import todolist.controller.Command;

import java.util.Arrays;
import java.util.Locale;

public class CommandParserImpl implements CommandParser {

    @Override
    public ParseResult parse(String source) {
        if (source == null) {
            return new ParseResult.Error("Source should not be null");
        }
        String[] sources = source.trim().split(" ", 2);
        String commandStr = sources[0].toUpperCase(Locale.ROOT);
        try {
            Command command = Command.valueOf(commandStr);
            String[] parameters = Arrays.copyOfRange(sources, 1, sources.length);
            return new ParseResult.Success(command, parameters);
        } catch (IllegalArgumentException e) {
            return new ParseResult.Error("Метод не найден");
        } catch (NullPointerException e) {
            return new ParseResult.Error("Ничего не введено");
        }
    }
}
