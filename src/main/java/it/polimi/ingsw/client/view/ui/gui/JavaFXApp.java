package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private Scene currentScene;
    private Stage mainWindow;


    public JavaFXApp() {
        GUI gui = (GUI) Client.getUI ();
        gui.getState ().buildScene (gui);
        this.currentScene = gui.getState ().getScene ();
    }


    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        Platform.runLater (() -> this.mainWindow.setScene (this.currentScene));
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainWindow = stage;
        stage.setTitle ("Master Of Renaissance");
        stage.setScene (currentScene);
        stage.show ();
    }

    public static void show () {
        Application.launch ();
    }
}
