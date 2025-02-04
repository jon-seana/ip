import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime byDate;
    protected String date;

    public Deadline(String description, LocalDateTime byDate) {
        super(description);
        this.byDate = byDate;
    }

    public Deadline(String description, String byDate) {
        super(description);
        this.date = byDate;
    }

    @Override
    public String saveData() {
        if (byDate != null) {
            return "[D]" + super.toString() + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + date + ")";
        }
    }

    @Override
    public String toString() {
        if (byDate != null) {
            return "[D]" + super.toString() + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a")) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + date + ")";
        }
    }
}
