package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import botzilla.command.Parser;

/**
 * Represents the class for the task deadline.
 * Contains methods to create deadline as well as relevant methods required for deadline.
 */
public class Deadline extends Task {
    protected LocalDateTime byDate;
    protected String date;

    /**
     * Constructor for the deadline class with LocalDateTime as the date and time type.
     *
     * @param description Description of deadline.
     * @param byDate Due date and time of description in LocalDateTime object type.
     */
    public Deadline(String description, LocalDateTime byDate) {
        super(description);
        this.byDate = byDate;
    }

    /**
     * Constructor for the deadline class with String as the date and time type.
     *
     * @param description Description of deadline.
     * @param byDate Due date and time of description in String object type.
     */
    public Deadline(String description, String byDate) {
        super(description);
        this.date = byDate;
    }

    /**
     * Method for creating deadline task and to check for errors and formatting issues.
     *
     * @param input Input.
     * @return Deadline Deadline.
     */
    public static Deadline createDeadline(String input) {
        assert input != null && !input.trim().isEmpty() : "Input should not be null";
        if (!input.contains(" /by ")) {
            return null;
        }
        String[] deadlineInput = input.split(" /by ");
        String description = deadlineInput[0].substring(9).trim().replaceAll("\\s+", " ");
        String date = deadlineInput[1].trim();
        if (description.isEmpty() || date.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime byDate = Parser.parseDate(date);
            return new Deadline(description, byDate);
        } catch (DateTimeParseException e) {
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
        if (byDate != null) {
            return "[D]" + super.toString()
                         + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        }
        return "[D]" + super.toString() + " (by: " + date + ")";
    }

    /**
     * Method for deadline toString implementation.
     *
     * @return String.
     */
    @Override
    public String toString() {
        if (byDate != null) {
            return "[D]" + super.toString()
                         + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        }
        return "[D]" + super.toString() + " (by: " + date + ")";
    }
}
