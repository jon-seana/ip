import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Botzilla {
    private static final String FILE_PATH = "src/main/tasks.txt";

    private static void ensureFileExists(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file");
        }
    }

    private static void loadTask(ArrayList<Task> tasks) {
        ensureFileExists(FILE_PATH);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String type = parts[0].substring(1, 2);
                boolean isDone = parts[0].length() > 4 && parts[0].substring(4, 5).equals("X");

                if (type.equals("T")) {
                    String description = line.substring(7).trim();
                    Todo toDo = new Todo(description);
                    if (isDone) {
                        toDo.markAsDone();
                    }
                    tasks.add(toDo);
                } else if (type.equals("D")) {
                    String description = line.substring(7, line.indexOf("(by:")).trim();
                    String byDate = line.substring(line.indexOf(":") + 2, line.indexOf(")")).trim();
                    Deadline deadline = new Deadline(description, byDate);
                    if (isDone) {
                        deadline.markAsDone();
                    }
                    tasks.add(deadline);
                } else if (type.equals("E")) {
                    String description = line.substring(7, line.indexOf("(from:")).trim();
                    String from = line.substring(line.indexOf("m:") + 3, line.indexOf("to:")).trim();
                    String to = line.substring(line.indexOf("o:") + 3, line.indexOf(")")).trim();
                    Event event = new Event(description, from, to);
                    if (isDone) {
                        event.markAsDone();
                    }
                    tasks.add(event);
                }
            }
        } catch (IOException error) {
            System.out.println("No tasks found!");
        }
    }

    public static void saveTask(ArrayList<Task> tasks) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : tasks) {
                writer.write(task.saveData() + "\n");
            }
            writer.close();
        } catch (IOException error) {
            System.out.println("Error Occurred! Trying again...");
        }
    }

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

        //loadTask(taskList);

        System.out.println(horizontalLine);
        System.out.println("\t Hello! I'm Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println(endFormat);

        loadTask(taskList);

        while (true) {
            String input = scanner.nextLine();
            String[] message = input.split(" ");

            if (input.trim().equals("list")) {
                if(taskList.isEmpty()) {
                    System.out.println(horizontalLine);
                    System.out.println("\t You have no tasks in your list.");
                    System.out.println(endFormat);
                } else {
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
                }
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
                        saveTask(taskList);
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
                        saveTask(taskList);
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
                    if (input.substring(5).trim().equals("")) {
                        System.out.println(horizontalLine);
                        System.out.println("\t Hi there! Please add at least one word after the command.");
                        System.out.println(endFormat);
                        continue;
                    } else {
                        taskList.add(new Todo(input.substring(5)));
                        saveTask(taskList);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please add a description for a todo task.");
                    System.out.println(endFormat);
                    continue;
                }
                numberOfTasks++;
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(taskList.size() - 1).toString());
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (message[0].equals("deadline")) {
                try {
                    String[] deadlineInput = input.split(" /by ");
                    String description = deadlineInput[0].substring(9);
                    String byDate = deadlineInput[1];
                    taskList.add(new Deadline(description, byDate));
                    saveTask(taskList);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please follow the format: deadline task /by time.");
                    System.out.println(endFormat);
                    continue;
                }
                numberOfTasks++;
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(taskList.size() - 1).toString());
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (message[0].equals("event")) {
                try {
                    String[] eventInput = input.split(" /from ");
                    String description = eventInput[0].substring(6);
                    String from = (eventInput[1].split(" /to "))[0];
                    String to = (eventInput[1].split(" /to "))[1];
                    taskList.add(new Event(description, from, to));
                    saveTask(taskList);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please follow the format: event task /from time /to time.");
                    System.out.println(endFormat);
                    continue;
                }
                numberOfTasks++;
                System.out.println(horizontalLine);
                System.out.println(taskFirstLine);
                System.out.println("\t   " + taskList.get(taskList.size() - 1).toString());
                System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(endFormat);
            } else if (message[0].equals("delete")) {
                try {
                    taskList.get(Integer.parseInt(message[1]) - 1);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(horizontalLine);
                    System.out.println("\t Hi there! Please enter a valid task number you want to delete." + "\n" + "\t The task number you provided may have been removed or may not exist at all.");
                    System.out.println(endFormat);
                    continue;
                }
                numberOfTasks--;
                System.out.println(horizontalLine);
                System.out.println("\t Noted. I've removed this task:");
                System.out.println("\t   " + taskList.get(Integer.parseInt(message[1]) - 1).toString());
                System.out.println("\t Now you have " + (numberOfTasks) + " tasks in the list.");
                System.out.println(endFormat);
                taskList.remove(Integer.parseInt(message[1]) - 1);
            } else {
                System.out.println(horizontalLine);
                System.out.println("\t Hey! I don't understand what you want me to do :(");
                System.out.println(endFormat);
            }
        }
        scanner.close();
    }
}