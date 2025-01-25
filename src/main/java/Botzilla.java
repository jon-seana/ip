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
        String markAndUnmark = horizontalLine + "\n" + "\t Error!! You have no tasks in your list, please add a task first and try again." + "\n" + endFormat;
        String markError = horizontalLine + "\n" + "\t Error!! Please enter a valid task number you want to mark as done." + "\n" + endFormat;
        String unmarkError = horizontalLine + "\n" + "\t Error!! Please enter a valid task number you want to mark as undone." + "\n" + endFormat;

        System.out.println(horizontalLine);
        System.out.println("\t Hello! I'm Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println(endFormat);

        while (true) {
            String input = scanner.nextLine();
            String[] message = input.split(" ");

            if (input.trim().equals("list")) {
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
            } else if (input.trim().equals("bye")) {
                System.out.println(horizontalLine);
                System.out.println("\t Bye. Hope to see you again soon!");
                System.out.println(horizontalLine);
                break;
            } else if (message[0].equals("mark")) {
                try {
                    if (taskList.isEmpty()) {
                        System.out.println(markAndUnmark);
                        continue;
                    } else {
                        taskList.get(Integer.parseInt(message[1]) - 1).markAsDone();
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(markError);
                    continue;
                }
                System.out.println(horizontalLine);
                System.out.println("\t Nice! I've marked this task as done:");
                System.out.println("\t   " + taskList.get(Integer.parseInt(message[1]) - 1).toString());
                System.out.println(endFormat);
            } else if (message[0].equals("unmark")) {
                try {
                    if (taskList.isEmpty()) {
                        System.out.println(markAndUnmark);
                        continue;
                    } else {
                        taskList.get(Integer.parseInt(message[1]) - 1).markAsUndone();
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(unmarkError);
                    continue;
                }
                System.out.println(horizontalLine);
                System.out.println("\t OK, I've marked this task as not done yet:");
                System.out.println("\t   " + taskList.get(Integer.parseInt(message[1]) - 1).toString());
                System.out.println(endFormat);
            } else if (message[0].equals("todo")) {
                try {
                    taskList.add(new Todo(input.substring(5)));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please add a description for a todo task.");
                    System.out.println(endFormat);
                    continue;
                }
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(numberOfTasks).toString());
                System.out.println("\t Now you have " + (numberOfTasks + 1) + " tasks in the list.");
                System.out.println(endFormat);
                numberOfTasks++;
            } else if (message[0].equals("deadline")) {
                try {
                    String[] deadlineInput = input.split(" /by ");
                    String description = deadlineInput[0].substring(9);
                    String byDate = deadlineInput[1];
                    taskList.add(new Deadline(description, byDate));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please follow the format: deadline task /by time.");
                    System.out.println(endFormat);
                    continue;
                }
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(numberOfTasks).toString());
                System.out.println("\t Now you have " + (numberOfTasks + 1) + " tasks in the list.");
                System.out.println(endFormat);
                numberOfTasks++;
            } else if (message[0].equals("event")) {
                try {
                    String[] eventInput = input.split(" /from ");
                    String description = eventInput[0].substring(6);
                    String from = (eventInput[1].split(" /to "))[0];
                    String to = (eventInput[1].split(" /to "))[1];
                    taskList.add(new Event(description, from, to));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please follow the format: event task /from time /to time.");
                    System.out.println(endFormat);
                    continue;
                }
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(numberOfTasks).toString());
                System.out.println("\t Now you have " + (numberOfTasks + 1) + " tasks in the list.");
                System.out.println(endFormat);
                numberOfTasks++;
            } else {
                System.out.println(horizontalLine);
                System.out.println("\t Hey! I don't understand what you want me to do :(");
                System.out.println(endFormat);
            }
        }
        scanner.close();
    }
}