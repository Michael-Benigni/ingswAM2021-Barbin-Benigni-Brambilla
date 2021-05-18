package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.exception.AlreadyUsedForProuctionException;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductionCardActionTest extends ActionTest {

    @Test
    void performTest1() throws Exception {
        StorableResource resource = new StorableResource(ResourceType.SERVANT, 2);
        DevelopmentCard developmentCard = game.getGameBoard().getDevelopmentCardGrid().getChoosenCard(2, 0, player1);
        player1.getPersonalBoard().getSlotDevelopmentCards(0).placeOnTop(developmentCard);
        player1.getPersonalBoard().getStrongbox().store(resource);
        ArrayList<PayAction> payActions = new ArrayList<>();
        StrongboxAction strongboxAction = new StrongboxAction("remove", resource);
        payActions.add(strongboxAction);
        ProductionCardAction productionCardAction = new ProductionCardAction(0, payActions);
        StartProductionAction startProductionAction = new StartProductionAction();
        startProductionAction.perform(game, player1);
        productionCardAction.perform(game, player1);

        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().size() == 1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().get(0).equals(resource));
        assertTrue(player1.getPersonalBoard().getStrongbox().getAllResources().isEmpty());

        try{
            productionCardAction.perform(game, player1);
            fail();
        }
        catch (AlreadyUsedForProuctionException e){
            assertTrue(true);
        }
    }
}