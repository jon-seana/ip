package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import botzilla.command.Parser;

/**
 * Represents the class for the task event.
 * Contains method for creating an event as well as relevant methods required for event.
 */
public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;

    /**
     * Constructor for event class with LocalDateTime as the date and time type.
     *
     * @param description Description of event.
     * @param from start date and time of event in LocalDateTime object type.
     * @param to end date and time of event in LocalDateTime object type.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.fromDate = from;
        this.toDate = to;
    }

    /**
     * Constructor for event class with String as the date and time type.
     *
     * @param description Description of event.
     * @param from start date and time of event in String object type.
     * @param to end date and time of event in String object type.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Method for creating event.
     *
     * @param input Input.
     * @return Event
     */
    public static Event createEvent(String input) {
        assert input != null && !input.trim().isEmpty() : "Input should not be null";
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            return null;
        }
        String[] eventInput = input.split(" /from ");
        String description = eventInput[0].substring(6).trim();
        if (description.isEmpty()) {
            return null;
        }
        String from = (eventInput[1].split(" /to "))[0].trim();
        String to = (eventInput[1].split(" /to "))[1].trim();
        if (from.isEmpty() || to.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime fromDate = Parser.parseDate(from);
            LocalDateTime toDate = Parser.parseDate(to);
            return new Event(description, fromDate, toDate);
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
        if (fromDate != null && toDate != null) {
            return "[E]" + super.toString()
                    + " (from: " + fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"))
                    + " to: " + toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        }
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Method for event toString implementation.
     *
     * @return String.
     */
    @Override
    public String toString() {
        if (fromDate != null && toDate != null) {
            return "[E]" + super.toString()
                    + " (from: " + fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"))
                    + " to: " + toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        }
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
