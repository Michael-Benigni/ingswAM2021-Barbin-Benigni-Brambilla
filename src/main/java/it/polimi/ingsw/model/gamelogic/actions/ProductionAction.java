package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.Producible;
import it.polimi.ingsw.model.gameresources.stores.UnboundedResourcesContainer;
import java.util.ArrayList;

class ProductionAction extends UNDOableAction {
    private final ArrayList<Integer> numSlots;

    public ProductionAction(ArrayList<Integer> numSlots, ArrayList<PayAction> payments) {
        super(payments);
        this.numSlots = numSlots;
    }

    @Override
    protected ActionType getType() {
        return ActionType.MUTUAL_EX;
    }

    @Override
    public void perform(Game game, Player player) throws Exception {
        ArrayList<DevelopmentCard> cardsForProduction = new ArrayList<>();
        UnboundedResourcesContainer costs = new UnboundedResourcesContainer();
        for (int slotIdx : numSlots) {
            SlotDevelopmentCards slot = player.getPersonalBoard().getSlotDevelopmentCards(slotIdx);
            DevelopmentCard card = slot.getTopCard();
            cardsForProduction.add(card);
            costs.storeAll(card.getConsumedResources());
        }
        payOrUndo(game, player, costs);
        for (DevelopmentCard card : cardsForProduction) {
            ArrayList<Producible> producibles = card.getProducedResources();
            for (Producible p : producibles)
                p.onProduced(player, game);
        }
    }
}
