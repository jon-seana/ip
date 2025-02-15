package botzilla;

import botzilla.command.Parser;
import botzilla.exception.BotzillaException;
import botzilla.storage.Storage;
import botzilla.task.TaskList;
import botzilla.ui.Ui;

/**
 * Represents the Botzilla main class.
 */
public class Botzilla {
    private static Ui ui;
    private static Parser parser;

    /**
     * The constructor for botzilla class.
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
     * @return The chatbot's response as a String.
     */
    public String getResponse(String input) {
        return parser.parseString(input);
    }

    /**
     * Starts the botzilla chatbot in an interactive loop
     * that will run until the user inputs the command "bye".
     */
    public void run() {
        while (true) {
            String input = ui.readLine();
            parser.parseString(input);
        }
    }

    /**
     * The main entry point for running the botzilla chatbot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Botzilla().run();
    }
}
