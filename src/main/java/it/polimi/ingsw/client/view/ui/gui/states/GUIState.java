package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.cli.states.ClientState;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;

import java.util.Objects;

public abstract class GUIState extends ClientState {

    protected abstract void setSceneInstance(Scene scene);

    @Override
    public GUIState getNextState() {
        return null;
    }

    public abstract Scene buildScene(GUI gui);

    protected abstract Scene getSceneInstance();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GUIState)) return false;
        GUIState state = (GUIState) o;
        return Objects.equals (getSceneInstance (), state.getSceneInstance ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getSceneInstance ());
    }
}
