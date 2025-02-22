package botzilla.command;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import botzilla.exception.BotzillaException;
import botzilla.storage.Storage;
import botzilla.task.Deadline;
import botzilla.task.Event;
import botzilla.task.TaskList;
import botzilla.task.Todo;
import botzilla.ui.Ui;

/**
 * The Parser class provides methods to parse user inputs.
 * It is done by extracting the first word of the input and then performing the relevant actions.
 */
public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    /**
     * Creates a Parser object.
     *
     * @param taskList Tasklist object.
     * @param storage Storage object.
     * @param ui Ui object.
     */
    public Parser(TaskList taskList, Storage storage, Ui ui) {
        this.taskList = taskList;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Chooses a method to execute based on the input command.
     * Extracts the first word of the input and then performs the relevant actions.
     *
     * @param input Input command entered by the user.
     * @return Returns a String object as a result from the parsed input command.
     */
    public String parseString(String input) {
        try {
            String trimmedInput = input.trim();
            if (trimmedInput.equals("list")) {
                return taskList.getTaskListString();
            } else if (trimmedInput.equals("bye")) {
                return ui.sayGoodByeString();
            } else if (trimmedInput.startsWith("mark")) {
                return handleMarkCommand(trimmedInput);
            } else if (trimmedInput.startsWith("unmark")) {
                return handleUnmarkCommand(trimmedInput);
            } else if (trimmedInput.startsWith("todo")) {
                return handleTodoCommand(trimmedInput);
            } else if (trimmedInput.startsWith("deadline")) {
                return handleDeadlineCommand(trimmedInput);
            } else if (trimmedInput.startsWith("event")) {
                return handleEventCommand(trimmedInput);
            } else if (trimmedInput.startsWith("delete")) {
                return handleDeleteCommand(trimmedInput);
            } else if (trimmedInput.startsWith("find")) {
                return handleFindCommand(trimmedInput);
            } else if (trimmedInput.equals("sort")) {
                return taskList.sortTaskList();
            } else {
                return ui.dontUnderstandString();
            }
        } catch (BotzillaException error) {
            return error.getMessage();
        }
    }

    // utility methods
    /**
     * Parses the index of the task to be marked or unmarked.
     * Adds checks to ensure that the index is a number and is within the bounds of the task list.
     *
     * @param input Input index entered by user to be marked or unmarked.
     * @return Index of the task to be marked or unmarked.
     * @throws BotzillaException If the index is not a number or is out of bounds.
     */
    private int parseIndex(String input) throws BotzillaException {
        try {
            String[] parts = input.split(" ");
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException | IndexOutOfBoundsException error) {
            throw new BotzillaException("Error!! Please enter a valid task number you want to mark/unmark as done.");
        }
    }

    /**
     * Checks for duplicate instructions within a single command input line.
     *
     * @param input Input command entered by user.
     * @throws BotzillaException If there are duplicate instructions within a single command input line.
     */
    private void duplicateInstrChecker(String input) throws BotzillaException {
        String[] tokens = input.split("\\s+");
        if (tokens.length > 2) {
            throw new BotzillaException("Hey! No duplicate instructions within a single command please.");
        }

    }

    /**
     * Parses a date string into a LocalDateTime object of a custom specified date and time format.
     *
     * @param date Date string.
     * @return LocalDateTime.
     */
    public static LocalDateTime parseDate(String date) throws DateTimeParseException {
        DateTimeFormatter dayFirstFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        DateTimeFormatter yearFirstFormat = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");
        if (date.contains("/")) {
            return LocalDateTime.parse(date, dayFirstFormat);
        } else if (date.contains("-")) {
            return LocalDateTime.parse(date, yearFirstFormat);
        } else {
            throw new DateTimeParseException("Invalid date format", date, 0);
        }
    }

    // Methods to help parseString method
    /**
     * Handles the mark command.
     * Marks a task as done.
     *
     * @param input Input command entered by user.
     * @return String.
     * @throws BotzillaException If the task list is empty or the index is invalid.
     */
    private String handleMarkCommand(String input) throws BotzillaException {
        duplicateInstrChecker(input);
        String trimmedInput = input.replaceAll("\\s+", " ");
        int index = parseIndex(trimmedInput);
        if (taskList.isEmpty()) {
            return ui.markUnmarkEmptyListString();
        }
        taskList.markDone(index - 1);
        storage.saveTask(taskList);
        return "Nice! I've marked this task as done:" + "\n"
                + taskList.getTask().get(index - 1).toString();
    }

    /**
     * Handles the unmark command.
     * Marks a task as not done.
     *
     * @param input Input command entered by user.
     * @return String.
     * @throws BotzillaException If the task list is empty or the index is invalid.
     */
    private String handleUnmarkCommand(String input) throws BotzillaException {
        duplicateInstrChecker(input);
        String trimmedInput = input.replaceAll("\\s+", " ");
        int index = parseIndex(trimmedInput);
        if (taskList.isEmpty()) {
            return ui.markUnmarkEmptyListString();
        }
        taskList.markUndone(index - 1);
        storage.saveTask(taskList);
        return "OK, I've marked this task as not done yet:" + "\n"
                + taskList.getTask().get(index - 1).toString();
    }

    /**
     * Handles the todo command.
     * Adds a todo task to the task list and saves it to the storage.
     *
     * @param input Input command entered by user.
     * @return String.
     * @throws BotzillaException If the description is empty.
     */
    private String handleTodoCommand(String input) throws BotzillaException {
        Todo createTodo = Todo.createTodo(input);
        if (createTodo == null) {
            throw new BotzillaException("Hi there! Please add a description for a todo task.");
        }
        taskList.addTask(createTodo);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    /**
     * Handles the deadline command.
     * Adds a deadline task to the task list and saves it to the storage.
     *
     * @param input Input command entered by user.
     * @return String.
     * @throws BotzillaException If the description or date is empty.
     */
    private String handleDeadlineCommand(String input) throws BotzillaException {
        Deadline createDeadline = Deadline.createDeadline(input);
        if (createDeadline == null) {
            throw new BotzillaException("Hi there! Please follow the format (e.g. deadline sleep /by 18/02/2025 1630)");
        }
        taskList.addTask(createDeadline);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    /**
     * Handles the event command.
     * Adds an event task to the task list and saves it to the storage.
     *
     * @param input Input command entered by user.
     * @return String.
     * @throws BotzillaException If the description or date is empty.
     */
    private String handleEventCommand(String input) throws BotzillaException {
        Event createEvent = Event.createEvent(input);
        if (createEvent == null) {
            throw new BotzillaException(
                    "Hi there! Please follow the format (e.g. event sleep /from 18/02/2025 1630 /to 18/02/2025 1730)");
        }
        taskList.addTask(createEvent);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    /**
     * Handles the delete command.
     * Deletes a task from the task list and saves the new task List to the storage.
     *
     * @param input Input command entered by user to delete a task.
     * @return String.
     * @throws BotzillaException If the task number is invalid or out of range.
     */
    private String handleDeleteCommand(String input) throws BotzillaException {
        duplicateInstrChecker(input);
        String trimmedInput = input.replaceAll("\\s+", " ");
        String deleted = taskList.deleteTask(trimmedInput);
        if (deleted == null) {
            throw new BotzillaException("Hi there! Please enter a valid task number you want to delete." + "\n"
                    + "The task number you provided may have been removed or may not exist at all.");
        }
        storage.saveTask(taskList);
        return "Noted. I've removed this task:" + "\n" + deleted
                + "\n" + "Now you have " + taskList.size() + " tasks in the list.";
    }

    /**
     * Handles the find command.
     * Finds tasks containing the keyword(s) and returns them as a string formatted in a list format.
     *
     * @param input Input command entered by user to find tasks by keywords.
     * @return String.
     * @throws BotzillaException If the keyword is empty or invalid format.
     */
    private String handleFindCommand(String input) throws BotzillaException {
        if (input.length() <= 5) {
            throw new BotzillaException("Find command requires this format:" + "\n" + "find <keyword(s)>");
        }
        String keyword = input.substring(4).replaceAll("\\s+", " ");
        return taskList.findTaskString(keyword);
    }
}
