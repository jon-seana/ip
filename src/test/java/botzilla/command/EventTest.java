package botzilla.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import botzilla.task.Event;
import botzilla.ui.Ui;

public class EventTest {
    @Test
    public void createEvent_validInput_slashFormat_success() {
        String input = "event meeting with james /from 05/02/2025 1530 /to 05/02/2025 1600";
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime from = LocalDateTime.parse("05/02/2025 1530", formatter);
        LocalDateTime to = LocalDateTime.parse("05/02/2025 1600", formatter);
        Event expectedEvent = new Event("meeting with james", from, to);
        assertEquals(expectedEvent.toString(), event.toString());
    }

    @Test
    public void createEvent_validInput_dashFormat_success() {
        String input = "event meeting with james /from 2025-02-05 1530 /to 2025-02-05 1600";
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        DateTimeFormatter formatterDash = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");
        LocalDateTime from = LocalDateTime.parse("2025-02-05 1530", formatterDash);
        LocalDateTime to = LocalDateTime.parse("2025-02-05 1600", formatterDash);
        Event expectedEvent = new Event("meeting with james", from, to);
        assertEquals(expectedEvent.toString(), event.toString());
    }

    /**
     * Test that missing the "/from" delimiter returns null.
     */
    @Test
    public void createEvent_invalidInput_missingFrom_returnsNull() {
        String input = "event meeting with james /to 05/02/2025 1600"; // Missing "/from"
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        assertNull(event, "Event should be null when '/from' delimiter is missing.");
    }

    /**
     * Test that missing the "/to" delimiter returns null.
     */
    @Test
    public void createEvent_invalidInput_missingTo_returnsNull() {
        String input = "event meeting with james /from 05/02/2025 1530"; // Missing "/to"
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        assertNull(event, "Event should be null when '/to' delimiter is missing.");
    }

    /**
     * Test that an empty description returns null.
     */
    @Test
    public void createEvent_invalidInput_emptyDescription_returnsNull() {
        String input = "event  /from 05/02/2025 1530 /to 05/02/2025 1600"; // Empty description
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        assertNull(event, "Event should be null when the description is empty.");
    }

    /**
     * Test that an empty 'from' date returns null.
     */
    @Test
    public void createEvent_invalidInput_emptyFrom_returnsNull() {
        String input = "event meeting with james /from  /to 05/02/2025 1600"; // Empty from
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        assertNull(event, "Event should be null when the 'from' date is empty.");
    }

    /**
     * Test that an empty 'to' date returns null.
     */
    @Test
    public void createEvent_invalidInput_emptyTo_returnsNull() {
        String input = "event meeting with james /from 05/02/2025 1530 /to "; // Empty to
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        assertNull(event, "Event should be null when the 'to' date is empty.");
    }

    /**
     * Test that an invalid date format returns null.
     */
    @Test
    public void createEvent_invalidInput_invalidDateFormat_returnsNull() {
        String input = "event meeting with james /from invalid /to 05/02/2025 1600";
        Ui ui = new Ui();
        Event event = Event.createEvent(input, ui);
        assertNull(event, "Event should be null when the date format is invalid.");
    }
}
