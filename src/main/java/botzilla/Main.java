package botzilla;

import java.io.IOException;

import botzilla.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Represents the GUI for botzilla using FXML.
 */
public class Main extends Application {
    private Botzilla botzilla = new Botzilla();

    /**
     * Starts the GUI for botzilla application.
     *
     * @param stage Stage.
     */
    @Override
    public void start(Stage stage) {
        try {
            assert Main.class.getResource("/view/MainWindow.fxml") != null : "MainWindow.fxml not found";
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            // Set up the main window
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Botzilla");
            stage.setResizable(true);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Botzilla.png")));
            // Set minimum height and width for the stage
            int height = 700;
            stage.setMinHeight(height);
            int width = 450;
            stage.setMinWidth(width);
            fxmlLoader.<MainWindow>getController().setBotzilla(botzilla);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
