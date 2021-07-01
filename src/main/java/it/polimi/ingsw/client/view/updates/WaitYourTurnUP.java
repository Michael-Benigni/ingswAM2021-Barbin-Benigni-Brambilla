package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class WaitYourTurnUP implements ViewUpdate {
    private final Integer currPlayer;
    private final String currPlayerName;

    public WaitYourTurnUP(Integer currPlayer, String currPlayerName) {
        this.currPlayer = currPlayer;
        this.currPlayerName = currPlayerName;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel ().getInfoMatch ().setCurrentPlayer(String.valueOf (currPlayer), "");
    }
}
