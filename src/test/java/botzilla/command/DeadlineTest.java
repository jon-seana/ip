package botzilla.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import botzilla.task.Deadline;

/**
 * Represents the test class for the Deadline class.
 */
public class DeadlineTest {
    /**
     * Test creating a Deadline with valid input using the slash date format.
     */
    @Test
    public void createDeadline_validInput_slashFormat_success() {
        String input = "deadline meeting bob /by 05/02/2025 1500";
        Deadline deadline = Deadline.createDeadline(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime expectedDateTime = LocalDateTime.parse("05/02/2025 1500", formatter);
        Deadline expected = new Deadline("meeting bob", expectedDateTime);
        assert deadline != null;
        assertEquals(expected.toString(), deadline.toString(),
                     "The created Deadline does not match the expected value.");
    }

    /**
     * Test creating a Deadline with valid input using the dash date format.
     */
    @Test
    public void createDeadline_validInput_dashFormat_success() {
        String input = "deadline meeting bob /by 2025-02-5 1500";
        Deadline deadline = Deadline.createDeadline(input);
        DateTimeFormatter formatterDash = DateTimeFormatter.ofPattern("yyyy-MM-d HHmm");
        LocalDateTime expectedDateTime = LocalDateTime.parse("2025-02-5 1500", formatterDash);
        Deadline expected = new Deadline("meeting bob", expectedDateTime);
        assert deadline != null;
        assertEquals(expected.toString(), deadline.toString(),
                     "The created Deadline (dash format) does not match the expected value.");
    }

    /**
     * Test that input missing the " /by " delimiter returns null.
     */
    @Test
    public void createDeadline_invalidInput_missingByDelimiter_returnsNull() {
        String input = "deadline meeting bob 05/02/2025 1500"; // Missing " /by "
        Deadline deadline = Deadline.createDeadline(input);
        assertNull(deadline, "Deadline should be null when the input is missing the '/by' delimiter.");
    }

    /**
     * Test that input with an empty description returns null.
     */
    @Test
    public void createDeadline_invalidInput_emptyDescription_returnsNull() {
        String input = "deadline   /by 05/02/2025 1500"; // Description is empty
        Deadline deadline = Deadline.createDeadline(input);
        assertNull(deadline, "Deadline should be null when the description is empty.");
    }

    /**
     * Test that input with an empty date returns null.
     */
    @Test
    public void createDeadline_invalidInput_emptyDate_returnsNull() {
        String input = "deadline meeting bob /by "; // Date is empty
        Deadline deadline = Deadline.createDeadline(input);
        assertNull(deadline, "Deadline should be null when the date is empty.");
    }

    /**
     * Test that input with an invalid date format returns null.
     */
    @Test
    public void createDeadline_invalidInput_invalidDateFormat_returnsNull() {
        String input = "deadline meeting bob /by 2025/20/05";
        Deadline deadline = Deadline.createDeadline(input);
        assertNull(deadline, "Deadline should be null when the date format is invalid.");
    }

    /**
     * Test the saveData() method for a Deadline created with a valid format.
     */
    @Test
    public void saveData_validDeadline_returnsCorrectString() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse("05/02/2025 1500", inputFormatter);
        Deadline deadline = new Deadline("meeting bob", dateTime);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
        String formattedDate = dateTime.format(outputFormatter);
        String expected = "[D][ ] meeting bob (by: " + formattedDate + ")";

        assertEquals(expected, deadline.toString(), "The saveData() method did not return the expected string.");
    }
}
