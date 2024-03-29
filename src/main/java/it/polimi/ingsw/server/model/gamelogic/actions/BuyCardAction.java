package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.server.model.exception.CardNotBuyableException;
import it.polimi.ingsw.server.model.exception.DevelopmentCardNotAddableException;
import it.polimi.ingsw.server.model.exception.SlotDevelopmentCardsIsFullException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.*;

class BuyCardAction implements MutualExclusiveAction {
    private final int row;
    private final int column;
    private final int slotIdx;
    private final ArrayList<PayAction> payActions;

    BuyCardAction(int row, int column, ArrayList<PayAction> fromWhereAndWhatRemove, int slotIdx) {
        this.payActions = fromWhereAndWhatRemove;
        this.row = row;
        this.column = column;
        this.slotIdx = slotIdx;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        DevelopmentCardsGrid cardsGrid = game.getGameBoard().getDevelopmentCardGrid();
        DevelopmentCard chosenCard = cardsGrid.getChosenCard (this.row, this.column, player);
        if(player.canBuy(chosenCard)) {
            SlotDevelopmentCards slot = player.getPersonalBoard().getSlotDevelopmentCards(this.slotIdx);
            UnboundedResourcesContainer costContainer = new UnboundedResourcesContainer().storeAll(chosenCard.getCost());
            for (PayAction payAction : payActions)
                payAction.payOrUndo(game, player, costContainer);
            if (!costContainer.getAllResources().isEmpty())
                game.getCurrentTurn().undo(game, player);
            else {
                try {
                    slot.placeOnTop (chosenCard);
                    cardsGrid.removeChosenCard (row, column);
                } catch (DevelopmentCardNotAddableException | SlotDevelopmentCardsIsFullException e) {
                    try{
                        game.getCurrentTurn ().undo (game, player);
                    }
                    catch(Exception ex){
                        throw e;
                    }
                }
                game.getCurrentTurn().clearCache();
            }
        }
        else {
            game.getCurrentTurn ().resetToken();
            throw new CardNotBuyableException ();
        }
    }
}
