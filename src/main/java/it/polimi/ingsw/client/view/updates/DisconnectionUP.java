package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;


public class DisconnectionUP implements ViewUpdate {
    private final String playerDisconnected;

    public DisconnectionUP(String playerDisconnected) {
        this.playerDisconnected = playerDisconnected;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getInfoMatch().removeUserFromRoom(playerDisconnected);
        controller.getUI ().notifyMessage (playerDisconnected + " disconnected" );
    }
}
