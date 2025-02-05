package botzilla.ui;

import botzilla.task.TaskList;
import botzilla.task.Task;
import botzilla.storage.Storage;

import java.util.Scanner;

public class Ui {
    private static final String taskFirstLine = "\t Got it. I've added this task:";
    private static final String horizontalLine = "\t_____________________________________________________________________";
    private static final String endFormat = horizontalLine + "\n" + " ";
    private final Scanner scanner;
    private final Storage storage = new Storage();

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void showGreeting() {
        System.out.println(horizontalLine);
        System.out.println("\t Hello! I'm botzilla.Botzilla");
        System.out.println("\t What can I do for you?");
        System.out.println(endFormat);
    }

    public void sayGoodBye() {
        System.out.println(horizontalLine);
        System.out.println("\t Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }

    public void listEmpty() {
        System.out.println(horizontalLine);
        System.out.println("\t You have no tasks in your list.");
        System.out.println(endFormat);
    }

    public void markUnmarkEmptyList() {
        System.out.println(horizontalLine + "\n" + "\t Error!! You have no tasks in your list, please add a task first and try again." + "\n" + endFormat);
    }

    public void deleteError() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please enter a valid task number you want to delete.");
        System.out.println("\t The task number you provided may have been removed or may not exist at all.");
        System.out.println(endFormat);
    }

    public void toDoIncomplete() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please add at least one word after the command.");
        System.out.println(endFormat);
    }

    public void toDoError() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please add a description for a todo task.");
        System.out.println(endFormat);
    }

    public void deadLineParse() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please follow the format: deadline task /by d/mm/yyyy HHmm.");
        System.out.println(endFormat);
    }

    public void eventParse() {
        System.out.println(horizontalLine);
        System.out.println("\t Hi there! Please follow the format: event task /from d/mm/yyyy HHmm /to d/mm/yyyy HHmm.");
        System.out.println(endFormat);
    }

    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void dontUnderstand() {
        System.out.println(horizontalLine);
        System.out.println("\t Hey! I don't understand what you want me to do :(");
        System.out.println(endFormat);
    }

    public void printOut(TaskList taskList, Task task) {
        taskList.addTask(task);
        storage.saveTask(taskList);
        System.out.println(horizontalLine);
        System.out.println(taskFirstLine);
        System.out.println("\t   " + taskList.getTasks().get(taskList.size() - 1).toString());
        System.out.println("\t Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(endFormat);
    }
}
