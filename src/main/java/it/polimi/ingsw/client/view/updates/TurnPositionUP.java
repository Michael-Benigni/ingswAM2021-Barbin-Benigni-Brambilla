package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class TurnPositionUP implements ViewUpdate {
    private final int turnPosition;

    public TurnPositionUP(int turnPosition) {
        this.turnPosition = turnPosition;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel ().getInfoMatch ().setPlayerPositionInTurn (turnPosition);
        controller.setNextState ();
    }
}
