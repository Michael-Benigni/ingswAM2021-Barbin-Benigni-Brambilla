package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class WaitYourTurnUP implements ViewUpdate {
    private final int currPlayer;

    public WaitYourTurnUP(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("It's the turn of player " + currPlayer + ". Wait your turn.");
    }
}
