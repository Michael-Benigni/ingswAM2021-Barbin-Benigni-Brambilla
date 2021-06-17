package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.states.GUIState;
import it.polimi.ingsw.client.view.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GUIState state = new GUIWaitingRoomState ((GUI) Client.getUI());
        stage.setTitle ("Master Of Renaissance");
        stage.setScene (state.getScene ());
        stage.show ();
    }

    public static void show () {
        Application.launch ();
    }
}
