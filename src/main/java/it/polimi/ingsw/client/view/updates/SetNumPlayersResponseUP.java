package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class SetNumPlayersResponseUP implements ViewUpdate {
    private final int numPlayers;

    public SetNumPlayersResponseUP(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("The number of players of the Game has been set to " + numPlayers);
        controller.getUI ().nextInputRequest ();
    }
}
