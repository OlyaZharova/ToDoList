package todolist.UI;

import java.io.Closeable;

public interface UI extends Closeable {

    void showToUser(String line);

    String getUserInput();

    void close();
}
