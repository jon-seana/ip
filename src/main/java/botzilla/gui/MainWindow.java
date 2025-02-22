package botzilla.gui;

import botzilla.Botzilla;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private Botzilla botzilla;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image botzillaImage = new Image(this.getClass().getResourceAsStream("/images/Botzilla.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getBotzillaDialog("Hello! I'm Botzilla. How can I help you today?", botzillaImage, "hello")
        );
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
     * Creates two dialog boxes, one echoing user input and the other containing botzilla's reply.
     * And then appends them to the dialog container.
     * Clears the user input after processing and exits the program if the user types "bye".
     */
    @FXML
    private void handleUserInput() {
        String userInputText = userInput.getText().toLowerCase();
        String botResponse = botzilla.getResponse(userInputText);
        String commandType = userInput.getText();
        assert botResponse != null : "Botzilla response should not be null";
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userInputText, userImage),
                DialogBox.getBotzillaDialog(botResponse, botzillaImage, commandType)
        );
        userInput.clear();

        // Exit the program if the user types "bye".
        if (userInputText.trim().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1)); // 1-second delay
            delay.setOnFinished(event -> System.exit(0));
            delay.play();

            // Disable input so the user can't keep typing while the program is exiting.
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
