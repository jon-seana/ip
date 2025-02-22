package botzilla.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import botzilla.command.Parser;
import botzilla.exception.BotzillaException;

/**
 * Represents the class for the task event.
 * Contains methods for creating an event as well as relevant methods required for event.
 */
public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;

    /**
     * Represents a constructor for event class with LocalDateTime as the date and time type.
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
     * Represents a constructor for event class with String as the date and time type.
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
     * Creates an event from user description input.
     *
     * @param input Input from user to describe the event task.
     * @return Event A new event object.
     */
    public static Event createEvent(String input) throws BotzillaException {
        assert input != null && !input.trim().isEmpty() : "Input should not be null";
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            return null;
        }
        try {
            // Description, from and to.
            String description = Objects.requireNonNull(checkEventDescription(input))[0];
            String from = Objects.requireNonNull(checkEventDescription(input))[1];
            String to = Objects.requireNonNull(checkEventDescription(input))[2];
            // From and To dates.
            LocalDateTime fromDate = Parser.parseDate(from);
            LocalDateTime toDate = Parser.parseDate(to);
            // To ensure that From and To dates are in the correct order.
            if (!fromDate.isBefore(toDate)) {
                throw new BotzillaException("From date and time should be before To date and time.");
            }
            return new Event(description, fromDate, toDate);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Checks the description of the event task to ensure it is of valid format.
     *
     * @param description Description of event task.
     * @return String[].
     * @throws ArrayIndexOutOfBoundsException If the event description is empty.
     */
    private static String[] checkEventDescription(String description) throws ArrayIndexOutOfBoundsException {
        String[] eventInput = description.split(" /from ");
        String eventDescription = eventInput[0].substring(6).trim().replaceAll("\\s+", " ");
        if (eventDescription.isEmpty()) {
            return null;
        }
        String from = (eventInput[1].split(" /to "))[0].trim();
        String to = (eventInput[1].split(" /to "))[1].trim();
        if (from.isEmpty() || to.isEmpty()) {
            return null;
        }
        String[] descriptionAndFromAndTo = new String[3];
        descriptionAndFromAndTo[0] = eventDescription;
        descriptionAndFromAndTo[1] = from;
        descriptionAndFromAndTo[2] = to;
        return descriptionAndFromAndTo;
    }

    /**
     * Formats the string according to the event task.
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
