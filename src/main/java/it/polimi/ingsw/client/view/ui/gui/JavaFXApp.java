package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.exceptions.AlreadyInstantiatedException;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.ui.gui.states.GUIPlayState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIWaitingRoomState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class JavaFXApp extends Application {
    private ArrayList<Scene> scenes;
    private Stage mainWindow;
    private GUI gui;
    private static JavaFXApp instance;


    public JavaFXApp() {
        gui = GUI.getInstance ();
        scenes = new ArrayList<> ();//getAllScenes();
        scenes.add (new GUIWaitingRoomState ()/*GUIPlayState()*/.buildScene (gui));
        instance = this;
    }

    private ArrayList<Scene> getAllScenes() {
        GUIState state = gui.getCurrentState ();
        Scene newScene = state.buildScene (gui);
        ArrayList<Scene> scenes = new ArrayList<> ();
        while (!scenes.contains (newScene)) {
            scenes.add (newScene);
            newScene = state.getNextState ().buildScene (gui);
        }
        return scenes;
    }

    //TODO: check if works
    public static JavaFXApp getInstance() {
        return instance;
    }

    public void setNextScene() {
        int currSceneIdx = scenes.indexOf (mainWindow.getScene ());
        mainWindow.setScene (scenes.get (currSceneIdx + 1));
        mainWindow.show ();
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        mainWindow.setTitle ("Master Of Renaissance");
        mainWindow.setScene (scenes.get (0));
        mainWindow.setWidth (650);
        mainWindow.setHeight (400);
        mainWindow.setResizable (true);
        mainWindow.setMaximized (true);
        mainWindow.setOnCloseRequest (e -> new MoveService (PlayMove.QUIT.getMove (), gui).start ());
        stage.show ();
    }

    public static void show () throws AlreadyInstantiatedException {
        if (instance == null)
            Application.launch ();
        else
            throw new AlreadyInstantiatedException ();
    }
}
