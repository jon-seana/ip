package botzilla;

import botzilla.command.Parser;
import botzilla.exception.BotzillaException;
import botzilla.storage.Storage;
import botzilla.task.TaskList;
import botzilla.ui.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private static Storage storage;
    private static TaskList tasks;
    private static Ui ui;
    private static Parser parser;
    private Botzilla botzilla;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image botzillaImage = new Image(this.getClass().getResourceAsStream("/images/Botzilla.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getBotzillaDialog("Hello! I'm Botzilla. How can I help you today?", botzillaImage)
        );
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(storage.loadTask());
        } catch (BotzillaException error) {
            ui.showErrorMessage("unable to load tasks!!");
            tasks = new TaskList();
        }
        parser = new Parser(tasks, storage, ui);
        botzilla = new Botzilla();
    }

    /**
     * Sets the botzilla object.
     *
     * @param bot Botzilla object.
     */
    public void setBotzilla(Botzilla bot) {
        this.botzilla = bot;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing botzilla's reply
     * and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userInputText = userInput.getText();
        String botResponse = botzilla.getResponse(userInputText);
        if (userInputText.trim().equals("bye")) {
            System.exit(0);
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userInputText, userImage),
                DialogBox.getBotzillaDialog(botResponse, botzillaImage)
        );
        userInput.clear();
    }
}
