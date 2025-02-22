package botzilla.task;

/**
 * Represents the general class for tasks.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Represents a constructor for task class.
     *
     * @param description Type of task to be done.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieves the status icon "X" or " ", to mark as done and not done respectively.
     *
     * @return String.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks isDone as true.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks isDone as false.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Formats the string according to the respective task's implementation.
     *
     * @return String.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
