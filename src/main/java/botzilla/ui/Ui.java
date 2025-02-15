package botzilla.ui;
import java.util.Scanner;

import botzilla.task.TaskList;

/**
 * Represents the class Ui for the replies given to users.
 */
public class Ui {
    private static final String taskFirstLine = "\t Got it. I've added this task:";
    private final Scanner scanner;

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
     * Method to return the goodbye message.
     *
     * @return String.
     */
    public String sayGoodByeString() {
        return "Bye. Hope to see you again soon!";
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
     * @return String.
     */
    public String getPrintOutString(TaskList taskList) {
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
     * Method to print out the custom warning message.
     *
     * @param errorMessage Error message to be printed to user.
     */
    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
