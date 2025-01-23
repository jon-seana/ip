import java.util.ArrayList;
import java.util.Scanner;

public class Botzilla {
    public static void main(String[] args) {
        // Variables
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();
        int numberOfTasks = 0;

        // Repetitive Strings
        String taskFirstLine = "\t Got it. I've added this task:";
        String horizontalLine = "\t_____________________________________________________________________";
        String endFormat = horizontalLine + "\n" + " ";

        System.out.println(horizontalLine);
        System.out.println("\t Hello! I'm Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println(horizontalLine);


        while (true) {
            String input = scanner.nextLine();
            String[] message = input.split(" ");

            if (input.equals("list")) {
                System.out.println(horizontalLine);
                System.out.println("\t Here are the tasks in your list:");
                int lengthOfList = taskList.size();
                for (int i = 0; i < lengthOfList; i++) {
                    if (taskList.get(i) != null) {
                        int b = i + 1;
                        System.out.println("\t " + b + "." + taskList.get(i).toString());
                    }
                }
                System.out.println(endFormat);
            } else if (input.equals("bye")) {
                System.out.println(horizontalLine);
                System.out.println("\t Bye. Hope to see you again soon!");
                System.out.println(horizontalLine);
                break;
            } else if (message[0].equals("mark")) {
                taskList.get(Integer.parseInt(message[1]) - 1).markAsDone();
                System.out.println(horizontalLine);
                System.out.println("\t " + "Nice! I've marked this task as done:");
                System.out.println("\t   " + taskList.get(Integer.parseInt(message[1]) - 1).toString());
                System.out.println(endFormat);
            } else if (message[0].equals("unmark")) {
                taskList.get(Integer.parseInt(message[1]) - 1).markAsUndone();
                System.out.println(horizontalLine);
                System.out.println("\t " + "OK, I've marked this task as not done yet:");
                System.out.println("\t   " + taskList.get(Integer.parseInt(message[1]) - 1).toString());
                System.out.println(endFormat);
            } else if (message[0].equals("todo")) {
                taskList.add(new Todo(input.substring(5)));
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(numberOfTasks).toString());
                System.out.println("\t Now you have " + (numberOfTasks + 1) + " tasks in the list.");
                System.out.println(endFormat);
                numberOfTasks++;
            } else if (message[0].equals("deadline")) {
                String[] deadlineInput = input.split(" /by ");
                String description = deadlineInput[0].substring(9);
                String byDate = deadlineInput[1];
                taskList.add(new Deadline(description, byDate));
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(numberOfTasks).toString());
                System.out.println("\t Now you have " + (numberOfTasks + 1) + " tasks in the list.");
                System.out.println(endFormat);
                numberOfTasks++;
            } else if (message[0].equals("event")) {
                String[] eventInput = input.split(" /from ");
                String description = eventInput[0].substring(6);
                String from = (eventInput[1].split(" /to "))[0];
                String to = (eventInput[1].split(" /to "))[1];
                taskList.add(new Event(description, from, to));
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(numberOfTasks).toString());
                System.out.println("\t Now you have " + (numberOfTasks + 1) + " tasks in the list.");
                System.out.println(endFormat);
                numberOfTasks++;
            } else {
                taskList.add(new Task(input));
                numberOfTasks++;
                System.out.println(horizontalLine);
                System.out.println("\t " + "added: " + input);
                System.out.println(endFormat);
            }
        }

        scanner.close();
    }
}

