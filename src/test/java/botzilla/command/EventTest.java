package botzilla.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import botzilla.task.Event;

/**
 * Represents the test class for the Event class.
 */
public class EventTest {
    /**
     * Test creating an Event with valid input using the slash date format.
     */
    @Test
    public void createEvent_validInput_slashFormat_success() throws Exception {
        String input = "event meeting with james /from 05/02/2025 1530 /to 05/02/2025 1600";
        Event event = Event.createEvent(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime from = LocalDateTime.parse("05/02/2025 1530", formatter);
        LocalDateTime to = LocalDateTime.parse("05/02/2025 1600", formatter);
        Event expectedEvent = new Event("meeting with james", from, to);
        assert event != null;
        assertEquals(expectedEvent.toString(), event.toString());
    }

    /**
     * Test creating an Event with valid input using the dash date format.
     */
    @Test
    public void createEvent_validInput_dashFormat_success() throws Exception {
        String input = "event meeting with james /from 2025-02-05 1530 /to 2025-02-05 1600";
        Event event = Event.createEvent(input);
        DateTimeFormatter formatterDash = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");
        LocalDateTime from = LocalDateTime.parse("2025-02-05 1530", formatterDash);
        LocalDateTime to = LocalDateTime.parse("2025-02-05 1600", formatterDash);
        Event expectedEvent = new Event("meeting with james", from, to);
        assert event != null;
        assertEquals(expectedEvent.toString(), event.toString());
    }

    /**
     * Test that missing the "/from" delimiter returns null.
     */
    @Test
    public void createEvent_invalidInput_missingFrom_returnsNull() throws Exception {
        String input = "event meeting with james /to 05/02/2025 1600"; // Missing "/from"
        Event event = Event.createEvent(input);
        assertNull(event, "Event should be null when '/from' delimiter is missing.");
    }

    /**
     * Test that missing the "/to" delimiter returns null.
     */
    @Test
    public void createEvent_invalidInput_missingTo_returnsNull() throws Exception {
        String input = "event meeting with james /from 05/02/2025 1530"; // Missing "/to"
        Event event = Event.createEvent(input);
        assertNull(event, "Event should be null when '/to' delimiter is missing.");
    }

    /**
     * Test that an empty description returns null.
     */
    @Test
    public void createEvent_invalidInput_emptyDescription_returnsNull() throws Exception {
        String input = "event  /from 05/02/2025 1530 /to 05/02/2025 1600"; // Empty description
        Event event = Event.createEvent(input);
        assertNull(event, "Event should be null when the description is empty.");
    }

    /**
     * Test that an empty 'from' date returns null.
     */
    @Test
    public void createEvent_invalidInput_emptyFrom_returnsNull() throws Exception {
        String input = "event meeting with james /from  /to 05/02/2025 1600"; // Empty from
        Event event = Event.createEvent(input);
        assertNull(event, "Event should be null when the 'from' date is empty.");
    }

    /**
     * Test that an empty 'to' date returns null.
     */
    @Test
    public void createEvent_invalidInput_emptyTo_returnsNull() throws Exception {
        String input = "event meeting with james /from 05/02/2025 1530 /to "; // Empty to
        Event event = Event.createEvent(input);
        assertNull(event, "Event should be null when the 'to' date is empty.");
    }

    /**
     * Test that an invalid date format returns null.
     */
    @Test
    public void createEvent_invalidInput_invalidDateFormat_returnsNull() throws Exception {
        String input = "event meeting with james /from 2025/20/05 /to 05/02/2025 1600";
        Event event = Event.createEvent(input);
        assertNull(event, "Event should be null when the date format is invalid.");
    }
}
