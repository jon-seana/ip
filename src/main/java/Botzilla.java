import java.util.ArrayList;
import java.util.Scanner;

public class Botzilla {
    public static void main(String[] args) {
        // Variables
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        System.out.println("\t_____________________________________________________________________");
        System.out.println("\t Hello! I'm Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println("\t_____________________________________________________________________");

        while (true) {
            String input = scanner.nextLine();
            String[] message = input.split(" ");

            if (input.equals("list")) {
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t Here are the tasks in your list:");
                int lengthOfList = taskList.size();
                for (int i = 0; i < lengthOfList; i++) {
                    if (taskList.get(i) != null) {
                        int b = i + 1;
                        System.out.println("\t " + b + "." + "[" + taskList.get(i).getStatusIcon() + "] " + taskList.get(i).getDescription());
                    }
                }
                System.out.println("\t_____________________________________________________________________");
                System.out.println(" ");
            } else if (input.equals("bye")) {
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t Bye. Hope to see you again soon!");
                System.out.println("\t_____________________________________________________________________");
                break;
            } else if (message[0].equals("mark")) {
                taskList.get(Integer.parseInt(message[1]) - 1).markAsDone();
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t " + "Nice! I've marked this task as done:");
                System.out.println("\t  " + "[" + taskList.get(Integer.parseInt(message[1]) - 1).getStatusIcon() + "] " + taskList.get(Integer.parseInt(message[1]) - 1).getDescription());
                System.out.println("\t_____________________________________________________________________");
                System.out.println(" ");
            } else if (message[0].equals("unmark")) {
                taskList.get(Integer.parseInt(message[1]) - 1).markAsUndone();
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t " + "OK, I've marked this task as not done yet:");
                System.out.println("\t  " + "[" + taskList.get(Integer.parseInt(message[1]) - 1).getStatusIcon() + "] " + taskList.get(Integer.parseInt(message[1]) - 1).getDescription());
                System.out.println("\t_____________________________________________________________________");
                System.out.println(" ");
            }
            else {
                taskList.add(new Task(input));
                System.out.println("\t_____________________________________________________________________");
                System.out.println("\t " + "added: " + input);
                System.out.println("\t_____________________________________________________________________");
                System.out.println(" ");
            }
        }

        scanner.close();
    }
}

