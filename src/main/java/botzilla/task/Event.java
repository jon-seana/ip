package botzilla.task;

import botzilla.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.fromDate = from;
        this.toDate = to;
    }
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Event createEvent(String input, Ui ui) {
        try {
            if (!input.contains(" /from ") || !input.contains(" /to ")) {
                ui.eventParse();
                return null;
            }
            String[] eventInput = input.split(" /from ");
            String description = eventInput[0].substring(6).trim();
            if (description.isEmpty()) {
                ui.eventParse();
                return null;
            }
            String from = (eventInput[1].split(" /to "))[0].trim();
            String to = (eventInput[1].split(" /to "))[1].trim();
            if (from.isEmpty() || to.isEmpty()) {
                ui.eventParse();
                return null;
            }
            LocalDateTime fromDate;
            LocalDateTime toDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");
            if (from.contains("/")) {
                fromDate = LocalDateTime.parse(from, formatter);
            } else if (from.contains("-")) {
                fromDate = LocalDateTime.parse(from, formatter2);
            } else {
                throw new DateTimeParseException("Invalid date format: " + from, from, 0);
            }
            if (to.contains("/")) {
                toDate = LocalDateTime.parse(to, formatter);
            } else if (to.contains("-")) {
                toDate = LocalDateTime.parse(to, formatter2);
            } else {
                throw new DateTimeParseException("Invalid date format: " + to, to, 0);
            }
            return new Event(description, fromDate, toDate);
        } catch (IndexOutOfBoundsException | DateTimeParseException error) {
            ui.eventParse();
            return null;
        }
    }

    @Override
    public String saveData() {
        if (fromDate != null && toDate != null) {
            return "[E]" + super.toString() + " (from: " + fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + " to: " + toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        } else {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }

    @Override
    public String toString() {
        if (fromDate != null && toDate != null) {
            return "[E]" + super.toString() + " (from: " + fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + " to: " + toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        } else {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }    }
}
