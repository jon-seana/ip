package botzilla;

import botzilla.command.Parser;
import botzilla.exception.BotzillaException;
import botzilla.storage.Storage;
import botzilla.task.TaskList;
import botzilla.ui.Ui;

/**
 * Represents the botzilla main class.
 */
public class Botzilla {
    private static Ui ui;
    private static Parser parser;

    /**
     * Represents a constructor for botzilla class.
     */
    public Botzilla() {
        ui = new Ui();
        Storage storage = new Storage();
        TaskList tasks;
        try {
            tasks = new TaskList(storage.loadTask());
        } catch (BotzillaException error) {
            ui.showErrorMessage("unable to load tasks!!");
            tasks = new TaskList();
        }
        parser = new Parser(tasks, storage, ui);
    }

    /**
     * Returns the response from the botzilla chatbot based on the user input command.
     *
     * @param input The user's input command.
     * @return String Botzilla's response to user input command as a String.
     */
    public String getResponse(String input) {
        return parser.parseString(input);
    }
}
