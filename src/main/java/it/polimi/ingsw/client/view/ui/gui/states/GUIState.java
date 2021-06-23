package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.cli.states.ClientState;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;

public abstract class GUIState extends ClientState {

    @Override
    public GUIState getNextState() {
        return null;
    }

    public abstract Scene buildScene(GUI gui);
}
