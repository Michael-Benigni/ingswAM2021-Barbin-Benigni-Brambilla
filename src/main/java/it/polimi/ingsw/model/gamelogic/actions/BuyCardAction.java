package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.UnboundedResourcesContainer;
import java.util.*;

class BuyCardAction extends Action {
    private final int row;
    private final int column;
    private final int slotIdx;
    private final ArrayList<PayAction> payActions;

    public BuyCardAction(int row, int column, ArrayList<PayAction> fromWhereAndWhatRemove, int slotIdx) {
        this.payActions = fromWhereAndWhatRemove;
        this.row = row;
        this.column = column;
        this.slotIdx = slotIdx;
    }

    @Override
    protected ActionType getType() {
        return ActionType.MUTUAL_EX;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        DevelopmentCardsGrid cardsGrid = game.getGameBoard().getDevelopmentCardGrid();
        DevelopmentCard chosenCard = cardsGrid.getChoosenCard(this.row, this.column, player);
        if(player.canBuy(chosenCard)) {
            SlotDevelopmentCards slot = player.getPersonalBoard().getSlotDevelopmentCards(this.slotIdx);
            UnboundedResourcesContainer costContainer = new UnboundedResourcesContainer().storeAll(chosenCard.getCost());
            for (PayAction payAction : payActions)
                payAction.payOrUndo(game, player);
            if (!costContainer.getAllResources().isEmpty())
                game.getCurrentTurn().undo(game, player);
            else {
                game.getCurrentTurn().clearCache();
                slot.placeOnTop(chosenCard);
                cardsGrid.removeChoosenCardFromGrid(row, column);
            }
        }
    }
}
