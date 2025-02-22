package botzilla.task;


/**
 * Represents the class for the task todo.
 * Contains methods to create todo as well as relevant methods required for todo.
 */
public class Todo extends Task {
    /**
     * Represents a constructor for the todo class.
     *
     * @param description Description of todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a todo from user description input.
     *
     * @param input Input from user to describe the todo task.
     * @return Todo A new todo object.
     */
    public static Todo createTodo(String input) {
        assert input != null && !input.trim().isEmpty() : "Input should not be null";
        try {
            String description = input.substring(5).trim().replaceAll("\\s+", " ");
            if (description.isEmpty()) {
                return null;
            }
            return new Todo(description);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Formats the string according to the todo task.
     *
     * @return String.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
