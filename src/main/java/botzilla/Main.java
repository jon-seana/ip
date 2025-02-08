package botzilla;

import java.io.IOException;

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

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Botzilla");
            stage.setResizable(true);
            stage.setMinHeight(600);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setBotzilla(botzilla);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
