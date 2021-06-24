package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;

public class YourTurnUP implements ViewUpdate {
    private final String additionalMsg;

    public YourTurnUP(String additionalMsg) {
        this.additionalMsg = additionalMsg;
    }

    @Override
    public void update(Controller controller) {
        InfoMatch infoMatch = controller.getModel ().getInfoMatch ();
        infoMatch.setCurrentPlayer (infoMatch.getPlayerPositionInTurn (), additionalMsg);
    }
}
