package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;

import java.util.ArrayDeque;

public abstract class GUIState extends ClientState {
    private Scene scene;

    public Scene getScene(){
        return this.scene;
    }

    public abstract void buildScene(GUI gui);

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

}
