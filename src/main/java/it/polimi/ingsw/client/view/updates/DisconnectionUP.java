package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class DisconnectionUP implements ViewUpdate {
    private final int playerDisconnected;

    public DisconnectionUP(int playerDisconnected) {
        this.playerDisconnected = playerDisconnected;
    }

    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("Player " + playerDisconnected + " with username \""
                + controller.getModel ().getInfoMatch ().getPlayerAt (playerDisconnected) + "\" disconnected" );
    }
}
