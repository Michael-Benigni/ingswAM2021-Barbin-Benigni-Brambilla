package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.AlreadyUsedForProductionException;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;

class ProductionCardAction implements ProductionAction {
    private final int numSlot;
    private final ArrayList<PayAction> payActions;

    ProductionCardAction(int numSlot, ArrayList<PayAction> payActions) {
        this.numSlot = numSlot;
        this.payActions = payActions;
    }

    @Override
    public void perform(Game game, Player player) throws Exception {
        UnboundedResourcesContainer costs = new UnboundedResourcesContainer();
        SlotDevelopmentCards slot = player.getPersonalBoard().getSlotDevelopmentCards(numSlot);
        DevelopmentCard card = slot.getTopCard();
        if (slot.isAvailableForProduction()) {
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
                slot.setAvailableForProduction(false);
            }
        }
        else
            throw new AlreadyUsedForProductionException();
    }
}

