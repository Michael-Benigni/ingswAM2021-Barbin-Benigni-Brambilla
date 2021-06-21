package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;

public class GUIGameOverState extends GUIState {
    @Override
    public GUIState getNextState() {
        return new GUIWaitingRoomState ();
    }

    @Override
    public void buildScene(GUI gui) {

    }
}
