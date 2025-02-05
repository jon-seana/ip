package botzilla.task;

import botzilla.ui.Ui;
import botzilla.exception.BotzillaException;

import java.util.ArrayList;

/**
 * Represents a class for common task related commands.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    private static final Ui ui = new Ui();
    private static final String horizontalLine = "\t_____________________________________________________________________";
    private static final String endFormat = horizontalLine + "\n" + " ";

    /**
     * Method for assigning an arraylist.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Method for assigning this.tasks to the tasks input parameter.
     *
     * @param tasks Tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Method to return the current number of tasks registered.
     *
     * @return int Number of task.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Method to add task into the arrayList.
     *
     * @param task Task.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Method to get arrayList.
     *
     * @return ArrayList<Task> List of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Method to mark a task done.
     *
     * @param index Task number to be marked done.
     * @throws BotzillaException Custom type of exception thrown.
     */
    public void markDone(int index) throws BotzillaException {
        if (index < 0 || index >= tasks.size()) {
            throw new BotzillaException(horizontalLine + "\n" + "\t Error!! Please enter a valid task number you want to mark as done." + "\n" + endFormat);
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Method to mark a task as undone.
     *
     * @param index Task number to be marked undone.
     * @throws BotzillaException Custom type of exception thrown.
     */
    public void markUndone(int index) throws BotzillaException {
        if (index < 0 || index >= tasks.size()) {
            throw new BotzillaException(horizontalLine + "\n" + "\t Error!! Please enter a valid task number you want to mark as undone." + "\n" + endFormat);
        }
        tasks.get(index).markAsUndone();
    }

    /**
     * Method to delete task.
     *
     * @param input Command input containing the task number to be deleted.
     * @param ui Ui.
     * @return String.
     */
    public String deleteTask(String input, Ui ui) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]);
            String deleted = tasks.get(index - 1).toString();
            tasks.remove(index - 1);
            return deleted;
        } catch (NumberFormatException | IndexOutOfBoundsException error) {
            ui.deleteError();
            return null;
        }
    }

    /**
     * Method to check if the taskList is empty.
     *
     * @return boolean.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Method to invoke the test(ArrayList<Task> tasks) method.
     */
    public void listTasks() {
        test(tasks);
    }

    private static void test(ArrayList<Task> tasks) {
        if(tasks.isEmpty()) {
            TaskList.ui.listEmpty();
        } else {
            System.out.println(TaskList.horizontalLine);
            System.out.println("\t Here are the tasks in your list:");
            int lengthOfList = tasks.size();
            for (int i = 0; i < lengthOfList; i++) {
                if (tasks.get(i) != null) {
                    int b = i + 1;
                    System.out.println("\t " + b + "." + tasks.get(i).toString());
                }
            }
            System.out.println(TaskList.endFormat);
        }
    }
}
