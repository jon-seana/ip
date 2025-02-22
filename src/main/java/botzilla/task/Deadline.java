package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import botzilla.command.Parser;

/**
 * Represents the class for the task deadline.
 * Contains methods to create deadline as well as relevant methods required for deadline.
 */
public class Deadline extends Task {
    protected LocalDateTime byDate;
    protected String date;

    /**
     * Represents a constructor for the deadline class with LocalDateTime as the date and time type.
     *
     * @param description Description of deadline.
     * @param byDate Due date and time of description in LocalDateTime object type.
     */
    public Deadline(String description, LocalDateTime byDate) {
        super(description);
        this.byDate = byDate;
    }

    /**
     * Represents a constructor for the deadline class with String as the date and time type.
     *
     * @param description Description of deadline.
     * @param byDate Due date and time of description in String object type.
     */
    public Deadline(String description, String byDate) {
        super(description);
        this.date = byDate;
    }

    /**
     * Creates a deadline from user description input.
     *
     * @param input Input from user to describe the deadline task.
     * @return Deadline A new deadline object.
     */
    public static Deadline createDeadline(String input) {
        assert input != null && !input.trim().isEmpty() : "Input should not be null";
        if (!input.contains(" /by ")) {
            return null;
        }
        try {
            // Date and description.
            String date = Objects.requireNonNull(checkDeadlineDescription(input))[0];
            String description = Objects.requireNonNull(checkDeadlineDescription(input))[1];
            // byDate variable.
            LocalDateTime byDate = Parser.parseDate(date);
            return new Deadline(description, byDate);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Checks the description of the deadline task to ensure it is of valid format.
     *
     * @param description Description of deadline task.
     * @return String[].
     * @throws ArrayIndexOutOfBoundsException If the deadline description is empty.
     */
    private static String[] checkDeadlineDescription(String description) throws ArrayIndexOutOfBoundsException {
        String[] deadlineInput = description.split(" /by ");
        String deadlineDescription = deadlineInput[0].substring(9).trim().replaceAll("\\s+", " ");
        String date = deadlineInput[1].trim();
        if (deadlineDescription.isEmpty() || date.isEmpty()) {
            return null;
        }
        String[] dateAndDescription = new String[2];
        dateAndDescription[0] = date;
        dateAndDescription[1] = deadlineDescription;
        return dateAndDescription;
    }

    /**
     * Formats the string according to the deadline task.
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
