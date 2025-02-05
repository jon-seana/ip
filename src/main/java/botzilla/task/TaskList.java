package botzilla.task;

import botzilla.ui.Ui;
import botzilla.exception.BotzillaException;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    private static final Ui ui = new Ui();
    private static final String horizontalLine = "\t_____________________________________________________________________";
    private static final String endFormat = horizontalLine + "\n" + " ";

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void markDone(int index) throws BotzillaException {
        if (index < 0 || index >= tasks.size()) {
            throw new BotzillaException(horizontalLine + "\n" + "\t Error!! Please enter a valid task number you want to mark as done." + "\n" + endFormat);
        }
        tasks.get(index).markAsDone();
    }

    public void markUndone(int index) throws BotzillaException {
        if (index < 0 || index >= tasks.size()) {
            throw new BotzillaException(horizontalLine + "\n" + "\t Error!! Please enter a valid task number you want to mark as undone." + "\n" + endFormat);
        }
        tasks.get(index).markAsUndone();
    }

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

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

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

    /**
     * Method to find a task from a keyword command input from user.
     *
     * @param keyword Command to be typed in by user.
     * @param ui Ui.
     */
    public void findTask(String keyword, Ui ui) {
        ArrayList<Task> resultOfSearch = new ArrayList<>();

        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                resultOfSearch.add(task);
            }
        }

        if (resultOfSearch.isEmpty()) {
            ui.findTaskError();
        } else {
            System.out.println(TaskList.horizontalLine);
            System.out.println("\t Here are the matching tasks in your list:");
            int lengthOfList = resultOfSearch.size();
            for (int i = 0; i < lengthOfList; i++) {
                if (resultOfSearch.get(i) != null) {
                    int b = i + 1;
                    System.out.println("\t " + b + "." + resultOfSearch.get(i).toString());
                }
            }
            System.out.println(TaskList.endFormat);
        }
    }
}
