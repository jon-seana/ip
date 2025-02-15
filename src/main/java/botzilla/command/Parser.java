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
            if (input.trim().equals("list")) {
                return taskList.getTaskListString();
            } else if (input.trim().equals("bye")) {
                return ui.sayGoodByeString();
            } else if (input.startsWith("mark")) {
                return handleMarkCommand(input);
            } else if (input.startsWith("unmark")) {
                return handleUnmarkCommand(input);
            } else if (input.startsWith("todo")) {
                return handleTodoCommand(input);
            } else if (input.startsWith("deadline")) {
                return handleDeadlineCommand(input);
            } else if (input.startsWith("event")) {
                return handleEventCommand(input);
            } else if (input.startsWith("delete")) {
                return handleDeleteCommand(input);
            } else if (input.startsWith("find")) {
                return handleFindCommand(input);
            } else if (input.trim().equals("sort")) {
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
            throw new BotzillaException("\t Error!! Please enter a valid task number you want to mark as done.");
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
        int index = parseIndex(input);
        if (taskList.isEmpty()) {
            return ui.markUnmarkEmptyListString();
        }
        taskList.markDone(index - 1);
        storage.saveTask(taskList);
        return "\t Nice! I've marked this task as done:" + "\n"
                + "\t   " + taskList.getTask().get(index - 1).toString();
    }

    private String handleUnmarkCommand(String input) throws BotzillaException {
        int index = parseIndex(input);
        if (taskList.isEmpty()) {
            return ui.markUnmarkEmptyListString();
        }
        taskList.markUndone(index - 1);
        storage.saveTask(taskList);
        return "\t OK, I've marked this task as not done yet:" + "\n"
                + "\t   " + taskList.getTask().get(index - 1).toString();
    }

    private String handleTodoCommand(String input) {
        Todo createTodo = Todo.createTodo(input);
        if (createTodo == null) {
            return "\t Hi there! Please add a description for a todo task.";
        }
        taskList.addTask(createTodo);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    private String handleDeadlineCommand(String input) {
        Deadline createDeadline = Deadline.createDeadline(input);
        if (createDeadline == null) {
            return "\t Hi there! Please follow the format: deadline task /by d/mm/yyyy HHmm.";
        }
        taskList.addTask(createDeadline);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    private String handleEventCommand(String input) throws BotzillaException {
        Event createEvent = Event.createEvent(input);
        if (createEvent == null) {
            return "\t Hi there! Please follow the format: event task /from d/mm/yyyy HHmm /to d/mm/yyyy HHmm.";
        }
        taskList.addTask(createEvent);
        storage.saveTask(taskList);
        return ui.getPrintOutString(taskList);
    }

    private String handleDeleteCommand(String input) {
        String deleted = taskList.deleteTask(input);
        if (deleted == null) {
            return "\t Hi there! Please enter a valid task number you want to delete." + "\n"
                    + "\t The task number you provided may have been removed or may not exist at all.";
        }
        storage.saveTask(taskList);
        return "\t Noted. I've removed this task:" + "\n" + "\t   " + deleted
                + "\n" + "\t Now you have " + taskList.size() + " tasks in the list.";
    }

    private String handleFindCommand(String input) throws BotzillaException {
        if (input.length() <= 5) {
            throw new BotzillaException("\t Find command requires this format:" + "\n" + "\t find <keyword(s)>");
        }
        String keyword = input.substring(4);
        return taskList.findTaskString(keyword);
    }
}
