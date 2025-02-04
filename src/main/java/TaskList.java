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

    public void deleteTask(int index) {
        tasks.remove(index);
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
}
