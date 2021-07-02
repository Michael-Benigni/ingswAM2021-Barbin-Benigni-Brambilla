package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;

public class YourTurnUP implements ViewUpdate {
    private final String additionalMsg;
    private final Integer numTurn;

    public YourTurnUP(String additionalMsg, Integer numTurn) {
        this.additionalMsg = additionalMsg;
        this.numTurn = numTurn;
    }

    @Override
    public void update(Controller controller) {
        InfoMatch infoMatch = controller.getModel ().getInfoMatch ();
        infoMatch.setCurrentPlayer (infoMatch.getPlayerPositionInTurn (), additionalMsg);
        infoMatch.setCurrentTurn(numTurn);
    }
}
