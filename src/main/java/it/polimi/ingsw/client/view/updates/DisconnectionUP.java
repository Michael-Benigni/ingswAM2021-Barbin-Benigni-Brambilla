package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class DisconnectionUP implements ViewUpdate {
    private final int playerDisconnected;

    public DisconnectionUP(int playerDisconnected) {
        this.playerDisconnected = playerDisconnected;
    }

    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("Player " + playerDisconnected + " with username "
                + view.getModel ().getInfoMatch ().getPlayerAt (playerDisconnected));
    }
}
