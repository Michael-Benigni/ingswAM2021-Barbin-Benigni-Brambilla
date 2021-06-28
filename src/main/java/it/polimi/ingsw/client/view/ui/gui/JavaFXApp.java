package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.exceptions.AlreadyInstantiatedException;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.ui.gui.states.GUIState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIWaitingRoomState;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;

public class JavaFXApp extends Application {
    private ArrayList<Scene> scenes;
    private static Stage mainWindow;
    private final GUI gui;
    private static JavaFXApp instance;
    private final static double fixedWidth = 1280;
    private final static double fixedHeight = 720;

    public static ReadOnlyDoubleProperty getFixedWidth() {
        return mainWindow.widthProperty ();
    }

    public static ReadOnlyDoubleProperty getFixedHeight() {
        return mainWindow.heightProperty ();
    }


    public JavaFXApp() {
        gui = GUI.getInstance ();
        instance = this;
    }

    private ArrayList<Scene> getAllScenes() {
        GUIState state = gui.getCurrentState ();
        Scene newScene = state.buildScene (gui);
        ArrayList<Scene> scenes = new ArrayList<> ();
        ArrayList<GUIState> states = new ArrayList<> ();
        int i = 0;
        while (!states.contains (state)) {
            scenes.add (newScene);
            states.add (state);
            state = state.getNextState ();
            newScene = state.buildScene (gui);
            System.out.println (i);
            i++;
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

    public void goToSecondSceneOnLeaderChanged(){
        int indexOfWelcomeScene = scenes.indexOf(GUIWaitingRoomState.getInstance().getWelcomeSceneInstance());
        mainWindow.setScene(scenes.get(indexOfWelcomeScene + 1));
        mainWindow.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;

        //mainWindow.setResizable (false);
        mainWindow.setMinWidth (fixedWidth);
        mainWindow.setMinHeight (fixedHeight);
        mainWindow.setWidth (fixedWidth);
        mainWindow.setHeight (fixedHeight);
        mainWindow.setTitle ("Master Of Renaissance");
        scenes = getAllScenes ();
        mainWindow.setScene (scenes.get (0));
        mainWindow.setOnCloseRequest (e -> new MoveService (PlayMove.QUIT.getMove (), gui).start ());
        stage.show ();
    }

    public static void show () throws AlreadyInstantiatedException {
        if (instance == null)
            Application.launch ();
        else
            throw new AlreadyInstantiatedException ();
    }

    public void setSecondScene(boolean isLeader){
        GUIWaitingRoomState.getInstance().setSecondScene(isLeader, scenes);
    }

    public Stage getWindow() {
        return mainWindow;
    }
}
