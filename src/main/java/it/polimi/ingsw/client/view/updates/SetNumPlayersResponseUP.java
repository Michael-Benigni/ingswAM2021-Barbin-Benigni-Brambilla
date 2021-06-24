package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class SetNumPlayersResponseUP implements ViewUpdate {
    private final int numPlayers;

    public SetNumPlayersResponseUP(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel ().getInfoMatch ().setWaitingRoomSize (numPlayers);
    }
}
