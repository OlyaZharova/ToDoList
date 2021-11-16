package todolist;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleUI implements Ui {

    private InputStream in;
    private PrintStream out;

    public ConsoleUI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void getLine(String line) {
        out.println(line);
    }

    @Override
    public String readLine() {
        String line;
        Scanner sc = new Scanner(in);
        line = sc.nextLine();
        return line;
    }
}
