package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.states.GUIState;
import it.polimi.ingsw.client.view.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private Scene scene;

    public JavaFXApp() {
        GUI gui = (GUI) Client.getUI ();
        gui.getState ().buildScene (gui);
        this.scene = gui.getState ().getScene ();
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle ("Master Of Renaissance");
        stage.setScene (scene);
        stage.show ();
    }

    public static void show () {
        Application.launch ();
    }
}
