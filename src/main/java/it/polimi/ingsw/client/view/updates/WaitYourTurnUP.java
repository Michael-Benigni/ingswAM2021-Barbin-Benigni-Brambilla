package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class WaitYourTurnUP implements ViewUpdate {
    private final int currPlayer;
    private final int currPlayerName;

    public WaitYourTurnUP(int currPlayer, int currPlayerName) {
        this.currPlayer = currPlayer;
        this.currPlayerName = currPlayerName;
    }

    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("It's the turn of " + currPlayerName + " (Player number " + currPlayer + ")" + ". Wait your turn.");
    }
}
