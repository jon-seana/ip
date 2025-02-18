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
 * Represents a class for the Parser constructor and parse method which takes in a String input and
 * selects the appropriate actions to be taken based on the type of input (e.g. todo, list, bye, etc...).
 */
public class Parser {
    private final TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    /**
     * The constructor for Parser class.
     *
     * @param taskList Tasklist.
     * @param storage Storage.
     * @param ui Ui.
     */
    public Parser(TaskList taskList, Storage storage, Ui ui) {
        this.taskList = taskList;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Choose a variety of actions depending on the String input. Returns a String output for that action.
     *
     * @param input Input command.
     * @return String.
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
    private int parseIndex(String input) throws BotzillaException {
        try {
            String[] parts = input.split(" ");
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException | IndexOutOfBoundsException error) {
            throw new BotzillaException("Error!! Please enter a valid task number you want to mark/unmark as done.");
        }
    }

    private void duplicateInstrChecker(String input) throws BotzillaException {
        String[] tokens = input.split("\\s+");
        if (tokens.length > 2) {
            throw new BotzillaException("Hey! No duplicate instructions within a single command please.");
        }

    }

    /**
     * Parses a date string into a LocalDateTime object.
     *
     * @param date Date string.
     * @return LocalDateTime.
     */
    public static LocalDateTime parseDate(String date) {
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

    private String handleTodoCommand(String input) throws BotzillaException {
        Todo createTodo = Todo.createTodo(input);
        if (createTodo == null) {
            throw new BotzillaException("Hi there! Please add a description for a todo task.");
        }
        taskList.addTask(createTodo);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    private String handleDeadlineCommand(String input) throws BotzillaException {
        Deadline createDeadline = Deadline.createDeadline(input);
        if (createDeadline == null) {
            throw new BotzillaException("Hi there! Please follow the format (e.g. deadline sleep /by 18/02/2025 1630)");
        }
        taskList.addTask(createDeadline);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

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

    private String handleFindCommand(String input) throws BotzillaException {
        if (input.length() <= 5) {
            throw new BotzillaException("Find command requires this format:" + "\n" + "find <keyword(s)>");
        }
        String keyword = input.substring(4).replaceAll("\\s+", " ");
        return taskList.findTaskString(keyword);
    }
}
