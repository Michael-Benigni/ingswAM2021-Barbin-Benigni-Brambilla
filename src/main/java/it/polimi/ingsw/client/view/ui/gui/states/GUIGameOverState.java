package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;

public class GUIGameOverState extends GUIState {
    @Override
    public GUIState getNextState() {
        return new GUIWaitingRoomState ();
    }

    public Scene buildScene(GUI gui) {

        return null;
    }
}
