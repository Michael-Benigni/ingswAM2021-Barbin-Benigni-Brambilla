package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import java.util.ArrayList;

public class BoardProductionAction extends Action {
    private final StorableResource produced;
    private final ArrayList<PayAction> payActions;

    public BoardProductionAction(StorableResource produced, ArrayList<PayAction> payActions) {
        this.produced = produced;
        this.payActions = payActions;
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
        produced.store(player);
    }
}
