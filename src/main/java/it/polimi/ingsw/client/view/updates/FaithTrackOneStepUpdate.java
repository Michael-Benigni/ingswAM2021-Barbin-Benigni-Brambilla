package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class FaithTrackOneStepUpdate implements ViewUpdate{
    private final int playerOrderReference;
    private final int playerPositionInFT;

    public FaithTrackOneStepUpdate(int playerOrderReference, int playerPositionInFT) {
        this.playerOrderReference = playerOrderReference;
        this.playerPositionInFT = playerPositionInFT;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getBoard().updatePlayerPosition(
                controller.getModel().getInfoMatch().getPlayerAt(playerOrderReference), this.playerPositionInFT);
    }
}
