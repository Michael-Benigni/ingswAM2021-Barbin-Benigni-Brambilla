package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private static Scene currentScene;
    private static Stage mainWindow;
    private static GUI gui;


    public JavaFXApp() {
        gui = (GUI) Client.getUI ();
        gui.getState ().buildScene (gui);
        currentScene = gui.getState ().getScene ();
    }


    public static void setCurrentScene(Scene currentScene) {
        JavaFXApp.currentScene = currentScene;
        mainWindow.setScene (JavaFXApp.currentScene);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        mainWindow.setTitle ("Master Of Renaissance");
        mainWindow.setScene (currentScene);
//        mainWindow.setFullScreen (true);
        mainWindow.setResizable (true);
        mainWindow.setOnCloseRequest (e -> new MoveService (PlayMove.QUIT.getMove (), gui).start ());
        stage.show ();
    }

    public static void show () {
        Application.launch ();
    }
}
