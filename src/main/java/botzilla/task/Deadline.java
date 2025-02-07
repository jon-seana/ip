package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import botzilla.ui.Ui;

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
     * @param ui Ui.
     * @return Deadline Deadline.
     */
    public static Deadline createDeadline(String input, Ui ui) {
        try {
            if (!input.contains(" /by ")) {
                ui.deadLineParse();
                return null;
            }
            String[] deadlineInput = input.split(" /by ");
            String description = deadlineInput[0].substring(9).trim();
            String date = deadlineInput[1].trim();
            if (description.isEmpty() || date.isEmpty()) {
                ui.deadLineParse();
                return null;
            }
            LocalDateTime byDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");
            if (date.contains("/")) {
                byDate = LocalDateTime.parse(date, formatter);
            } else if (date.contains("-")) {
                byDate = LocalDateTime.parse(date, formatter2);
            } else {
                throw new DateTimeParseException("Invalid date format", date, 0);
            }
            return new Deadline(description, byDate);
        } catch (IndexOutOfBoundsException | DateTimeParseException error) {
            ui.deadLineParse();
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
        } else {
            return "[D]" + super.toString() + " (by: " + date + ")";
        }
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
        } else {
            return "[D]" + super.toString() + " (by: " + date + ")";
        }
    }
}
