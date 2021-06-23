package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.exceptions.AlreadyInstantiatedException;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.ui.gui.states.GUIPlayState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIWaitingRoomState;
import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class JavaFXApp extends Application {
    private ArrayList<Scene> scenes;
    private static Stage mainWindow;
    private GUI gui;
    private static JavaFXApp instance;
    private final static double fixedWidth = 1300;
    private final static double fixedHeight = 700;

    public static ReadOnlyObjectProperty<Double> getFixedWidth() {
        return mainWindow.widthProperty ().asObject ();
    }

    public static ReadOnlyObjectProperty<Double> getFixedHeight() {
        return mainWindow.heightProperty ().asObject ();
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

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        mainWindow.setWidth (fixedWidth);
        mainWindow.setHeight (fixedHeight);
        mainWindow.setTitle ("Master Of Renaissance");
        scenes = getAllScenes ();
        mainWindow.setScene (scenes.get (0));
        //mainWindow.setFullScreen (true);

        mainWindow.setOnCloseRequest (e -> new MoveService (PlayMove.QUIT.getMove (), gui).start ());

        stage.show ();
    }

    public static void show () throws AlreadyInstantiatedException {
        if (instance == null)
            Application.launch ();
        else
            throw new AlreadyInstantiatedException ();
    }

    public void setStartMatchScene(boolean isLeader) {
        mainWindow.setScene (GUIWaitingRoomState.getStartMatchScene(isLeader));
        mainWindow.show ();
    }

    public void enableButtonStartGame() {
        GUIWaitingRoomState.getInstance().getStartButton().setDisable(false);
    }
}
