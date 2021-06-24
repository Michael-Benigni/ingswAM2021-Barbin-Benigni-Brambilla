package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;

public class ReconnectionResponse implements ViewUpdate {
    private final Integer playerPosition;

    public ReconnectionResponse(Integer playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public void update(Controller controller) {
        InfoMatch info = controller.getModel ().getInfoMatch ();
        if (info.getPlayerPositionInTurn () == playerPosition) {
            controller.getUI ().notifyMessage ("You are reconnected!");
        } else
            controller.getUI ().notifyMessage ("The player number " + playerPosition + " with username " + info.getPlayerAt (playerPosition) + " is reconnected!");
    }
}
