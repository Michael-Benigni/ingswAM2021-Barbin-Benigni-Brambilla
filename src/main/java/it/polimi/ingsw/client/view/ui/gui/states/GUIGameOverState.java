package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;

public class GUIGameOverState extends GUIState {
    private static Scene scene;

    @Override
    protected void setSceneInstance(Scene scene) {
        GUIGameOverState.scene = scene;
    }

    @Override
    public GUIState getNextState() {
        return GUIWaitingRoomState.getInstance();
    }

    public Scene buildScene(GUI gui) {

        return null;
    }

    @Override
    protected Scene getSceneInstance() {
        return scene;
    }
}
