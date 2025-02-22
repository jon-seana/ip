package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import botzilla.exception.BotzillaException;

/**
 * Represents a class for a collection of common task related methods.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Assigns an empty arrayList to the tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Assigns current tasks to the tasks input parameter.
     *
     * @param tasks Tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the arrayList.
     *
     * @return int Number of task.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the arrayList.
     *
     * @param task Task.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the arrayList of tasks.
     *
     * @return ArrayList (Type: Task) List of tasks.
     */
    public ArrayList<Task> getTask() {
        return tasks;
    }

    /**
     * Marks a task as done.
     *
     * @param index Task number to be marked done.
     * @throws BotzillaException When index input by user is out of the acceptable range.
     */
    public void markDone(int index) throws BotzillaException {
        if (index < 0 || index >= tasks.size()) {
            throw new BotzillaException("Error!! Please enter a valid task number you want to mark as done.");
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as undone.
     *
     * @param index Task number to be marked undone.
     * @throws BotzillaException When index input by user is out of the acceptable range.
     */
    public void markUndone(int index) throws BotzillaException {
        if (index < 0 || index >= tasks.size()) {
            throw new BotzillaException("Error!! Please enter a valid task number you want to mark as undone.");
        }
        tasks.get(index).markAsUndone();
    }

    /**
     * Deletes a task from the arrayList.
     *
     * @param input Command input from user containing the task number to be deleted.
     * @return String The deleted task description.
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
     * Checks if the arrayList is empty.
     *
     * @return boolean.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the task list in a string format.
     *
     * @return String.
     */
    public String getTaskListString() {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        }
        return buildTaskList().toString();
    }

    /**
     * Builds the task list in a string format using StringBuilder.
     *
     * @return StringBuilder.
     */
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
     * Finds a task in the arrayList based on the keyword input by user.
     *
     * @param keyword A task that the user wants to find in the arrayList.
     * @return String.
     */
    public String findTaskString(String keyword) {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        }
        ArrayList<Task> resultOfSearch = new ArrayList<>();

        // Finds the task in the arrayList based on the keyword input by user.
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
     * Sorts the task list for tasks type of deadlines and events in ascending order of date and time.
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

    /**
     * Filters the task list for tasks of type event.
     *
     * @return String.
     */
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

    /**
     * Sorts the events based on the start date and time in ascending order.
     *
     * @param eventList A list of events.
     * @return String A string format of the sorted event.
     */
    private String sortEventsOnStartDate(ArrayList<String> eventList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        // Sort based on the start date and time of each event in ascending order.
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

    /**
     * Extracts the event start date and time from the event task description.
     *
     * @param taskString The event task description in string format.
     * @param formatter The custom date and time format.
     * @return LocalDateTime The start date and time of the event.
     */
    private LocalDateTime extractEventStartDate(String taskString, DateTimeFormatter formatter) {
        int startIndex = taskString.indexOf("from:") + 5;
        int endIndex = taskString.indexOf("to:") - 1;
        String date = taskString.substring(startIndex, endIndex).trim();
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * Filters the task list for tasks of type deadline.
     *
     * @return String.
     */
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

    /**
     * Sorts the deadlines based on the by date and time in ascending order.
     *
     * @param deadlineList A list of deadlines.
     * @return String A string format of the sorted deadlines.
     */
    private String sortDeadlinesOnStartDate(ArrayList<String> deadlineList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        // Sort based on the by date and time of each deadline in ascending order.
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

    /**
     * Extracts the deadline by date and time from the deadline task description.
     *
     * @param taskString The deadline task description in string format.
     * @param formatter The custom date and time format.
     * @return LocalDateTime The deadline by date and time.
     */
    private LocalDateTime extractDeadlineDate(String taskString, DateTimeFormatter formatter) {
        int startIndex = taskString.indexOf("by:") + 4;
        int endIndex = taskString.indexOf(")");
        String date = taskString.substring(startIndex, endIndex).trim();
        return LocalDateTime.parse(date, formatter);
    }
}
