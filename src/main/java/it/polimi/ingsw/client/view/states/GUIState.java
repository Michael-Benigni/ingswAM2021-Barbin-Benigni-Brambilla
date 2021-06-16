package it.polimi.ingsw.client.view.states;

import javafx.scene.Scene;

public abstract class GUIState extends ClientState{
    private Scene scene;

    public GUIState() {
        this.scene = buildScene();
    }

    public Scene getScene(){
        return this.scene;
    }

    public Scene buildScene(){
        return null;
    }

}
