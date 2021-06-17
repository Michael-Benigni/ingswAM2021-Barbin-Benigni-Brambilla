package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;

import java.util.ArrayDeque;

public abstract class GUIState extends ClientState {
    private final Scene scene;

    public GUIState(GUI gui) {
        this.scene = buildScene(gui);
    }

    public Scene getScene(){
        return this.scene;
    }

    public Scene buildScene(GUI gui){
        return null;
    }

}
