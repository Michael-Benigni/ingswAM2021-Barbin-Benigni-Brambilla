package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class SetNumPlayersResponseUP implements ViewUpdate {
    private final int numPlayers;

    public SetNumPlayersResponseUP(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("The number of players of the Game has been set to " + numPlayers);
        view.getUI ().nextInputRequest ();
    }
}
