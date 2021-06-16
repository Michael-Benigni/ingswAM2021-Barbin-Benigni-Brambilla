package it.polimi.ingsw.server.model.gamelogic.actions;

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
        return (StorableResource) this.resourceToPay.clone();
    }


    public abstract Action getUndoAction();

    void payOrUndo (Game game, Player player, UnboundedResourcesContainer cost) throws Exception {
        try {
            perform(game, player);
            cost.remove(getResource());
            game.getCurrentTurn().addUndoableAction(this);
        } catch (Exception e) {
            this.getUndoAction().perform(game, player);
        }
    }

    public void setResource(StorableResource resource) {
        this.resourceToPay = resource;
    }
}
