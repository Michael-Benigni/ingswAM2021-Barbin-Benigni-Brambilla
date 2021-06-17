package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;


public abstract class PayAction implements Action {
    public enum StoreOrRemove {
        STORE(),
        REMOVE();
    }
    private final StoreOrRemove storeOrRemove;
    private StorableResource resourceToPay;

    protected PayAction(StorableResource resourceToPay, StoreOrRemove storeOrRemove) {
        this.resourceToPay = resourceToPay;
        this.storeOrRemove = storeOrRemove;
    }

    public StoreOrRemove getStoreOrRemove() {
        return storeOrRemove;
    }

    protected StorableResource getResource() {
        return this.resourceToPay.clone();
    }


    public abstract PayAction getUndoAction();

    void payOrUndo (Game game, Player player, UnboundedResourcesContainer cost) throws Exception {
        PayAction undoAction = this.getUndoAction ();
        if (getResource ().containedIn (player) && getStoreOrRemove ().equals (StoreOrRemove.REMOVE)) {
            // if the perform method throws an exception, means that in the warehouse or in the strongbox
            // it has been not possible to remove the target resources. Nothing changes in warehouse and in
            // strongbox in these cases, so we can simply ignore the exception.
            try {
                perform (game, player);
            } catch (Exception ignored) {
                return;
            }
            try {
                cost.remove (getResource ());
                game.getCurrentTurn ().addUndoableAction (this);
            } catch (NegativeResourceAmountException e) {
                cost.remove (getResource ().decreaseAmount (e.getRemainder ()));
                this.setResource (getResource ().decreaseAmount (e.getRemainder ()));
                undoAction.setResource (e.getRemainder ());
                undoAction.perform (game, player);
            } catch (Exception e) {
                undoAction.perform (game, player);
            }
        }
    }

    public void setResource(StorableResource resource) {
        this.resourceToPay = resource;
    }
}
