package botzilla.task;

/**
 * Represents the general class for tasks.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * The constructor for task class.
     *
     * @param description Type of task to be done.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Method to get status icon "X" or " ", which is a blank.
     *
     * @return String.
     */
    public String getStatusIcon() {
        return  (isDone ? "X" : " ");
    }

    /**
     * Method to make isDone = true.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Method to make isDone = false.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Method for creating a string when data is saved.
     *
     * @return String.
     */
    public String saveData() {
        return description;
    }

    /**
     * Method for task toString implementation.
     *
     * @return String.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
