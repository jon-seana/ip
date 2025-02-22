package botzilla.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import botzilla.task.Todo;

public class TodoTest {
    /**
     * Test creating a Todo with valid input.
     */
    @Test
    public void createTodo_validInput_success() {
        String input = "todo read book";
        Todo todo = Todo.createTodo(input);
        assertNotNull(todo, "Todo should not be null for valid input.");
        String expected = "[T][ ] read book";
        assertEquals(expected, todo.toString(), "The created Todo does not match the expected output.");
    }

    /**
     * Test that extra spaces in the Todo input are trimmed and normalized.
     */
    @Test
    public void createTodo_validInput_extraSpaces_success() {
        String input = "todo   read    book  ";
        Todo todo = Todo.createTodo(input);
        assertNotNull(todo, "Todo should not be null for valid input with extra spaces.");
        String expected = "[T][ ] read book";
        assertEquals(expected, todo.toString(), "The created Todo should normalize extra spaces in the description.");
    }

    /**
     * Test that an input with an empty description returns null.
     */
    @Test
    public void createTodo_invalidInput_emptyDescription_returnsNull_success() {
        String input = "todo    "; // After substring(5) and trim, description is empty.
        Todo todo = Todo.createTodo(input);
        assertNull(todo, "Todo should be null when the description is empty.");
    }

    /**
     * Test that an input that is too short (causing substring to throw an exception) returns null.
     */
    @Test
    public void createTodo_invalidInput_tooShort_returnsNull_success() {
        String input = "tod"; // Less than 5 characters; will cause IndexOutOfBoundsException.
        Todo todo = Todo.createTodo(input);
        assertNull(todo, "Todo should be null when input is too short.");
    }
}
