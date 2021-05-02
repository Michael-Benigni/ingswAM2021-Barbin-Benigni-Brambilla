package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.Producible;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.UnboundedResourcesContainer;
import java.util.ArrayList;

class ProductionAction extends Action {
    private final int numSlot;
    private final ArrayList<PayAction> payActions;

    public ProductionAction(int numSlot, ArrayList<PayAction> payActions) {
        this.numSlot = numSlot;
        this.payActions = payActions;
    }

    @Override
    protected ActionType getType() {
        return ActionType.MUTUAL_EX;
    }

    // TODO: how to check that the user send the production command 2 times for the same card
    @Override
    public void perform(Game game, Player player) throws Exception {
        UnboundedResourcesContainer costs = new UnboundedResourcesContainer();
        SlotDevelopmentCards slot = player.getPersonalBoard().getSlotDevelopmentCards(numSlot);
        DevelopmentCard card = slot.getTopCard();
        costs.storeAll(card.getConsumedResources());
        for (PayAction payAction : payActions)
            payAction.payOrUndo(game, player, costs);
        if (!costs.getAllResources().isEmpty())
            game.getCurrentTurn().undo(game, player);
        else {
            ArrayList<Producible> producible = card.getProducedResources();
            for (Producible p : producible)
                p.onProduced(player, game);
            game.getCurrentTurn().clearCache();
        }
    }
}

class EndProductionAction extends Action {

    /**
     * @return the type of the Action
     */
    @Override
    protected ActionType getType() {
        return ActionType.END;
    }

    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        ArrayList<StorableResource> resources = player.getPersonalBoard().getTempContainer().getAllResources();
        player.getPersonalBoard().getStrongbox().storeAll(resources);
        new EndTurnAction().perform(game, player);
    }
}

