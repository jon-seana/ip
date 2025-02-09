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
    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;
    private static Parser parser;

    /**
     * The constructor for botzilla class.
     */
    public Botzilla() {
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(storage.loadTask());
        } catch (BotzillaException error) {
            ui.showErrorMessage("unable to load tasks!!");
            tasks = new TaskList();
        }
        parser = new Parser(tasks, storage, ui);
    }

    /**
     * Method to get the String response from the botzilla chatbot.
     *
     * @param input Input command from user.
     * @return String.
     */
    public String getResponse(String input) {
        return parser.parseString(input);
    }

    /**
     * Method for executing the botzilla chatbot.
     */
    public void run() {
        ui.showGreeting();
        while (true) {
            String input = ui.readLine();
            parser.parse(input);
        }
    }

    /**
     * The main method for entry into the botzilla chatbot.
     *
     * @param args Arguments to be input by user.
     */
    public static void main(String[] args) {
        new Botzilla().run();
    }
}
