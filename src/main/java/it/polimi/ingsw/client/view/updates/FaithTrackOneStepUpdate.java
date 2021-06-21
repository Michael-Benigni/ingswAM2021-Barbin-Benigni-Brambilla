package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class FaithTrackOneStepUpdate implements ViewUpdate{
    private final String player;
    private final int playerPositionInFT;

    public FaithTrackOneStepUpdate(String player, int playerPositionInFT) {
        this.player = player;
        this.playerPositionInFT = playerPositionInFT;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getBoard().updatePlayerPosition(player, this.playerPositionInFT);
    }
}
