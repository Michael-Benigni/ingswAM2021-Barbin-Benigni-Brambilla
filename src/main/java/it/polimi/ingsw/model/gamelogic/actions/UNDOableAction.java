package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.exception.NotEnoughResourcesException;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.UnboundedResourcesContainer;
import java.util.ArrayList;

abstract class UNDOableAction extends Action {
    private final ArrayList<PayAction> fromWhereAndWhatRemove;

    protected UNDOableAction(ArrayList<PayAction> fromWhereAndWhatRemove) {
        super();
        this.fromWhereAndWhatRemove = fromWhereAndWhatRemove;
    }

    /**
     *
     * @param game
     * @param player
     * @param cost
     * @return all the paid Actions if all have been paid
     * @throws Exception
     */
    void payOrUndo(Game game, Player player, UnboundedResourcesContainer cost) throws Exception {
        ArrayList<PayAction> paidActions = new ArrayList<>();
        for (PayAction payAction : this.fromWhereAndWhatRemove) {
            try {
                payAction.perform(game, player);
                cost.remove(payAction.getResource());
            } catch (Exception e) {
                undo(paidActions, game, player);
                // TODO: pass the message of the exception e.getMsg() for ex.
                throw new Exception();
            }
            paidActions.add(payAction);
        }
        if (!cost.getAllResources().isEmpty()) {
            undo(paidActions, game, player);
            throw new NotEnoughResourcesException();
        }
    }

    private void undo(ArrayList<PayAction> actions, Game game, Player player) throws Exception {
        for (PayAction payActionToUNDO : actions) {
            Action action = payActionToUNDO.undoAction();
            action.perform(game, player);
        }
    }
}
