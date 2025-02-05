package botzilla.task;

import botzilla.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
