import java.util.Scanner;

public class ToDoList {
    public static void main(String[] args) {
        CommandImpl commandImpl = new CommandImpl();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().trim();
        while (!command.equals("stop")) {
            String action = command.split(" ")[0];
            String argument = getArgument(command, action);
            switch (action) {
                case ("add"):
                    if (argument.equals("")) {
                        System.out.println("Task don't input");
                    } else {
                        commandImpl.addTask(argument);
                    }
                    break;
                case ("close"):
                    if (!argument.equals("")) {
                        try {
                            Integer closeId = Integer.parseInt(argument);
                            commandImpl.closeTask(closeId);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Incorrect ID");
                        }
                    } else {
                        System.out.println("Incorrect ID");
                    }
                    break;
                case ("delete"):
                    if (!argument.equals("")) {
                        try {
                            Integer deleteId = Integer.parseInt(argument);
                            commandImpl.deleteTask(deleteId);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Incorrect ID");
                        }
                    } else {
                        System.out.println("Incorrect ID");
                    }
                    break;
                case ("list"):
                    if (argument.equals("")) {
                        commandImpl.list("open");
                    } else {
                        commandImpl.list(argument);
                    }
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
            System.out.println("");
            command = scanner.nextLine();
        }
        scanner.close();
    }

    static String getArgument(String command, String action) {
        String argument;
        argument = command.replaceFirst(action, "").trim();
        return argument;
    }
}
