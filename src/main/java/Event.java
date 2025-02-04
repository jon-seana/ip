import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
