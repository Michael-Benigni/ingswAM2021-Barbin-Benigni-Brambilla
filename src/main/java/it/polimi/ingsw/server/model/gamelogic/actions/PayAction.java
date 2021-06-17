package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;


public abstract class PayAction implements Action {
    private StorableResource resourceToPay;

    protected PayAction(StorableResource resourceToPay) {
        this.resourceToPay = resourceToPay;
    }

    protected StorableResource getResource() {
        return this.resourceToPay.clone();
    }


    public abstract PayAction getUndoAction();

    void payOrUndo (Game game, Player player, UnboundedResourcesContainer cost) throws Exception {
        try {
            perform(game, player);
            PayAction undoAction = this.getUndoAction ();
            try {
                cost.remove(getResource());
            } catch (NegativeResourceAmountException e) {
                undoAction.setResource (e.getRemainder ());
                undoAction.perform (game, player);
            } catch (NotContainedResourceException ignored) {
                undoAction.perform (game, player);
            }
            game.getCurrentTurn().addUndoableAction(this);
        } catch (Exception e) {
            game.getCurrentTurn ().undo (game, player);
        }
    }

    public void setResource(StorableResource resource) {
        this.resourceToPay = resource;
    }
}
