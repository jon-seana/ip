package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import botzilla.exception.BotzillaException;

/**
 * Represents a class for common task related commands.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

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
     * @return ArrayList (Type: Task) List of tasks.
     */
    public ArrayList<Task> getTask() {
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
            throw new BotzillaException("Error!! Please enter a valid task number you want to mark as done.");
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
            throw new BotzillaException("Error!! Please enter a valid task number you want to mark as undone.");
        }
        tasks.get(index).markAsUndone();
    }

    /**
     * Method to delete task.
     *
     * @param input Command input containing the task number to be deleted.
     * @return String.
     */
    public String deleteTask(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]);
            String deleted = tasks.get(index - 1).toString();
            tasks.remove(index - 1);
            return deleted;
        } catch (NumberFormatException | IndexOutOfBoundsException error) {
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
     * Method to return the list of tasks in a string format.
     *
     * @return String.
     */
    public String getTaskListString() {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        }
        return buildTaskList().toString();
    }

    private StringBuilder buildTaskList() {
        StringBuilder taskListString = new StringBuilder();
        taskListString.append("Here are the tasks in your list:");
        int lengthOfList = tasks.size();
        for (int i = 0; i < lengthOfList; i++) {
            Task task = tasks.get(i);
            if (task != null) {
                int taskNumber = i + 1;
                taskListString.append("\n").append(taskNumber).append(".")
                                                              .append(tasks.get(i).toString());
            }
        }
        return taskListString;
    }

    /**
     * Method to find a list of tasks from a keyword command input from user.
     *
     * @param keyword Command to be typed in by user.
     * @return String.
     */
    public String findTaskString(String keyword) {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        }
        ArrayList<Task> resultOfSearch = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                resultOfSearch.add(task);
            }
        }
        if (resultOfSearch.isEmpty()) {
            return "Error!! No matching tasks found.";
        }
        StringBuilder findTaskString = new StringBuilder();
        findTaskString.append("Here are the matching tasks in your list:");
        int lengthOfList = resultOfSearch.size();
        for (int i = 0; i < lengthOfList; i++) {
            Task task = resultOfSearch.get(i);
            if (task != null) {
                int taskNumber = i + 1;
                findTaskString.append("\n").append(taskNumber).append(".")
                                                              .append(resultOfSearch.get(i).toString());
            }
        }
        return findTaskString.toString();
    }

    /**
     * The method used to sort time sensitive tasks in ascending order.
     *
     * @return String.
     */
    public String sortTaskList() {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        }
        String sortedDeadlines = sortDeadlines();
        String sortedEvents = sortEvents();
        return sortedDeadlines + "\n\n" + sortedEvents;
    }

    private String sortEvents() {
        ArrayList<String> eventList = new ArrayList<>();
        for (Task task : tasks) {
            String taskString = task.toString();
            String typeOfTask = taskString.substring(1, 2);
            if (typeOfTask.equals("E")) {
                eventList.add(taskString);
            }
        }
        return sortEventsOnStartDate(eventList);
    }

    private String sortEventsOnStartDate(ArrayList<String> eventList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        // Only sort based on the start date of each event
        eventList.sort((s1, s2) -> {
            LocalDateTime firstDate = extractEventStartDate(s1, formatter);
            LocalDateTime secondDate = extractEventStartDate(s2, formatter);
            return firstDate.compareTo(secondDate);
        });
        StringBuilder sortedEvents = new StringBuilder();
        sortedEvents.append("Here are the sorted events in ascending order:");
        int taskNumber = 1;
        for (String event : eventList) {
            sortedEvents.append("\n").append(taskNumber++)
                                     .append(". ").append(event);
        }
        return sortedEvents.toString();
    }

    private LocalDateTime extractEventStartDate(String taskString, DateTimeFormatter formatter) {
        int startIndex = taskString.indexOf("from:") + 5;
        int endIndex = taskString.indexOf("to:") - 1;
        String date = taskString.substring(startIndex, endIndex).trim();
        return LocalDateTime.parse(date, formatter);
    }

    private String sortDeadlines() {
        ArrayList<String> deadlineList = new ArrayList<>();
        for (Task task : tasks) {
            String taskString = task.toString();
            String typeOfTask = taskString.substring(1, 2);
            if (typeOfTask.equals("D")) {
                deadlineList.add(taskString);
            }
        }
        return sortDeadlinesOnStartDate(deadlineList);
    }

    private String sortDeadlinesOnStartDate(ArrayList<String> deadlineList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        deadlineList.sort((s1, s2) -> {
            LocalDateTime firstDate = extractDeadlineDate(s1, formatter);
            LocalDateTime secondDate = extractDeadlineDate(s2, formatter);
            return firstDate.compareTo(secondDate);
        });
        StringBuilder sortedDeadlines = new StringBuilder();
        sortedDeadlines.append("Here are the sorted deadlines in ascending order:");
        int taskNumber = 1;
        for (String deadline : deadlineList) {
            sortedDeadlines.append("\n").append(taskNumber++)
                                        .append(". ").append(deadline);
        }
        return sortedDeadlines.toString();
    }

    private LocalDateTime extractDeadlineDate(String taskString, DateTimeFormatter formatter) {
        int startIndex = taskString.indexOf("by:") + 4;
        int endIndex = taskString.indexOf(")");
        String date = taskString.substring(startIndex, endIndex).trim();
        return LocalDateTime.parse(date, formatter);
    }
}
