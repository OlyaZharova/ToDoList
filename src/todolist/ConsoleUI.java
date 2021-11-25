package todolist;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleUI implements UI {

    private InputStream in;
    private PrintStream out;
    private Scanner sc;

    public ConsoleUI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        sc = new Scanner(in);
    }

    @Override
    public void showToUser(String line) {
        out.println(line);
    }

    @Override
    public String getUserInput() {
        String line;
        line = sc.nextLine();
        return line;
    }

    public void closeScanner() {
        sc.close();
    }
}
