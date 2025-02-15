package botzilla.gui;

import java.io.IOException;

import botzilla.Botzilla;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Botzilla using FXML.
 */
public class Main extends Application {
    private Botzilla botzilla = new Botzilla();

    /**
     * Starts the GUI application.
     *
     * @param stage Stage.
     */
    @Override
    public void start(Stage stage) {
        try {
            assert Main.class.getResource("/view/MainWindow.fxml") != null : "MainWindow.fxml not found";
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Botzilla");
            stage.setResizable(true);
            int height = 600;
            stage.setMinHeight(height);
            int width = 417;
            stage.setMinWidth(width);
            fxmlLoader.<MainWindow>getController().setBotzilla(botzilla);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
