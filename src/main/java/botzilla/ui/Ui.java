package botzilla.ui;
import java.util.Scanner;

import botzilla.task.TaskList;

/**
 * Represents the class Ui for the replies given to users.
 */
public class Ui {
    private static final String taskFirstLine = "Got it. I've added this task:";
    private final Scanner scanner;

    /**
     * Represents a constructor for the Ui class.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the goodbye message.
     *
     * @return String.
     */
    public String sayGoodByeString() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns the mark and unmark of task warning message when list is empty.
     *
     * @return String.
     */
    public String markUnmarkEmptyListString() {
        return "Error!! You have no tasks in your list, please add a task first and try again.";
    }

    /**
     * Returns the message after executing a task input from user.
     *
     * @param taskList Tasklist.
     * @return String.
     */
    public String getPrintOutString(TaskList taskList) {
        return taskFirstLine + "\n" + taskList.getTask().get(taskList.size() - 1).toString() + "\n"
                + "Now you have " + taskList.size() + " tasks in the list.";
    }

    /**
     * Returns a warning message for invalid commands.
     *
     * @return String.
     */
    public String dontUnderstandString() {
        return "Hey! I don't understand what you want me to do :(";
    }

    /**
     * Prints a custom warning message for errors to the command line interface.
     *
     * @param errorMessage Error message to be shown in command line interface.
     */
    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
