package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class TurnPositionUP implements ViewUpdate {
    private final int turnPosition;

    public TurnPositionUP(int turnPosition) {
        this.turnPosition = turnPosition;
    }

    @Override
    public void update(View view) {
        view.getUI().notifyMessage ("You are the " + turnPosition + "° player!");
        view.readyForNextMove ();
    }
}
