package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;

import java.util.ArrayList;

public class ExtraBoardProductionAction extends Action {
    private final ArrayList<PayAction> payActions;
    private final int numExtraPower;

    public ExtraBoardProductionAction(ArrayList<PayAction> payActions, int numExtraPower) {
        this.payActions = payActions;
        this.numExtraPower = numExtraPower;
    }

    /**
     * @return the type of the Action
     */
    @Override
    protected ActionType getType() {
        return null;
    }

    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        for (PayAction action : payActions)
            action.payOrUndo(game, player);
        game.getCurrentTurn().clearCache();
        //player.getPersonalBoard().activateExtraProductionPower();
    }
}
