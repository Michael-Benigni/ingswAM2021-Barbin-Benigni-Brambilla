package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class WaitYourTurnUP implements ViewUpdate {
    private final int currPlayer;

    public WaitYourTurnUP(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("It's the turn of player " + currPlayer + ". Wait your turn.");
    }
}
