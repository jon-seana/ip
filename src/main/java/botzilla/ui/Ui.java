package botzilla.ui;
import java.util.Scanner;

import botzilla.storage.Storage;
import botzilla.task.Task;
import botzilla.task.TaskList;

/**
 * Represents the class Ui for the replies given to users.
 */
public class Ui {
    private static final String taskFirstLine = "\t Got it. I've added this task:";
    private static final String horizontalLine =
            "\t_____________________________________________________________________";
    private static final String endFormat = horizontalLine + "\n" + " ";
    private final Scanner scanner;
    private final Storage storage = new Storage();

    /**
     * The constructor for the Ui class.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Method to read the next line of input from user.
     *
     * @return String.
     */
    public String readLine() {
        return scanner.nextLine();
    }

    /**
     * Method to print out the greeting message.
     */
    public void showGreeting() {
        System.out.println(horizontalLine);
        System.out.println("\t Hello! I'm Botzilla.");
        System.out.println("\t What can I do for you?");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the goodbye message.
     */
    public void sayGoodBye() {
        System.out.println(horizontalLine);
        System.out.println("\t Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }

    /**
     * Method to return the goodbye message.
     *
     * @return String.
     */
    public String sayGoodByeString() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Method to print out the empty list warning message.
     */
    public void listEmpty() {
        System.out.println(horizontalLine);
        System.out.println("\t You have no tasks in your list.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the mark and unmark of empty list warning message.
     */
    public void markUnmarkEmptyList() {
        System.out.println(horizontalLine + "\n"
                                   + "\t Error!! You have no tasks in your list, please add a task first and try again."
                                   + "\n" + endFormat);
    }

    /**
     * Method to return the mark and unmark of empty list warning message.
     *
     * @return String.
     */
    public String markUnmarkEmptyListString() {
        return "Error!! You have no tasks in your list, please add a task first and try again.";
    }

    /**
     * Method to print out the message after executing a task input from user.
     *
     * @param taskList Tasklist.
     * @param task Task.
     * @return String.
     */
    public String getPrintOutString(TaskList taskList, Task task) {
        return taskFirstLine + "\n" + "\t   " + taskList.getTask().get(taskList.size() - 1).toString() + "\n"
                + "\t Now you have " + taskList.size() + " tasks in the list.";
    }

    /**
     * Method to print out the warning message for invalid command.
     *
     * @return String.
     */
    public String dontUnderstandString() {
        return "Hey! I don't understand what you want me to do :(";
    }

    /**
     * Method to print out the delete warning message.
     */
    public void deleteError() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please enter a valid task number you want to delete.");
        System.out.println("\t The task number you provided may have been removed or may not exist at all.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the todo incomplete warning message.
     */
    public void toDoIncomplete() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please add at least one word after the command.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the todo warning message.
     */
    public void toDoError() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please add a description for a todo task.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the deadline warning message.
     */
    public void deadLineParse() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please follow the format: deadline task /by d/mm/yyyy HHmm.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the event warning message.
     */
    public void eventParse() {
        System.out.println(horizontalLine);
        System.out.println(
                "\t Hi there! Please follow the format: event task /from d/mm/yyyy HHmm /to d/mm/yyyy HHmm.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the custom warning message.
     *
     * @param errorMessage Error message to be printed to user.
     */
    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Method to print out invalid command warning message.
     */
    public void dontUnderstand() {
        System.out.println(horizontalLine);
        System.out.println("\t Hey! I don't understand what you want me to do :(");
        System.out.println(endFormat);
    }

    /**
     * Method to print out the message after executing a task input from user.
     *
     * @param taskList Tasklist.
     * @param task Task.
     */
    public void printOut(TaskList taskList, Task task) {
        taskList.addTask(task);
        storage.saveTask(taskList);
        System.out.println(horizontalLine);
        System.out.println(taskFirstLine);
        System.out.println("\t   " + taskList.getTask().get(taskList.size() - 1).toString());
        System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(endFormat);
    }

    /**
     * Method to print out find task warning message.
     */
    public void findTaskError() {
        System.out.println(horizontalLine);
        System.out.println("\t No matching task found!");
        System.out.println(endFormat);
    }
}
