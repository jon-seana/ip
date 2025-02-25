package botzilla.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's and botzilla image.
 * And a label containing text from the speaker and response from botzilla.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Initializes the dialog box object.
     *
     * @param text Text to be displayed, from user and botzilla.
     * @param img Image to be displayed, from user and botzilla.
     */
    private DialogBox(String text, Image img) {
        assert text != null : "DialogBox text cannot be null";
        assert img != null : "DialogBox image cannot be null";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        dialog.getStyleClass().add("reply-label");
        Collections.reverse(tmp);
        this.getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Returns a dialog box with the user's dialog response and image.
     *
     * @param text Text to be displayed from user.
     * @param img User image to be displayed.
     * @return DialogBox.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "User text cannot be null";
        assert img != null : "User image cannot be null";
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.TOP_RIGHT);
        return db;
    }

    /** Changes the colour style of the dialog box based on the command type. */
    private void changeDialogStyle(String commandType) {
        if (commandType == null || commandType.trim().isEmpty()) {
            return;
        }
        String typeOfCommand = commandType.trim().split(" ")[0];
        switch(typeOfCommand) {
        case "todo":
            dialog.getStyleClass().add("add-label");
            break;
        case "mark":
            dialog.getStyleClass().add("marked-label");
            break;
        case "delete":
            dialog.getStyleClass().add("delete-label");
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Returns a dialog box with the botzilla's dialog response and image.
     *
     * @param text Text to be displayed from botzilla.
     * @param img Botzilla image to be displayed.
     * @return DialogBox.
     */
    public static DialogBox getBotzillaDialog(String text, Image img, String commandType) {
        assert text != null : "Botzilla text cannot be null";
        assert img != null : "Botzilla image cannot be null";
        var db = new DialogBox(text, img);
        db.changeDialogStyle(commandType.toLowerCase());
        db.flip();
        return db;
    }
}
