package botzilla.task;

import botzilla.ui.Ui;
import botzilla.task.Task;
import botzilla.task.TaskList;

/**
 * Represents the class for todo task.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }
    /**
     * Creates a new Todo task from the given input.
     * The input should start with "todo" followed by a description.
     * If the description is missing or empty, the corresponding UI error message is shown.
     *
     * @param input the full user input string.
     * @param ui the UI instance used to display error messages.
     * @return a new Todo task if the description is valid; otherwise, returns null.
     */
    public static Todo createTodo(String input, Ui ui) {
        try {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                ui.toDoIncomplete();
                return null;
            }
            return new Todo(description);
        } catch (IndexOutOfBoundsException e) {
            ui.toDoError();
            return null;
        }
    }

    /**
     * Method for creating a string when data is saved.
     *
     * @return String.
     */
    @Override
    public String saveData() {
        return "[T]" + super.toString();
    }

    /**
     * Method for todo toString implementation.
     *
     * @return String.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
