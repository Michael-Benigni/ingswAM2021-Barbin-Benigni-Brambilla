package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BuyCardActionTest extends ActionTest{

    @BeforeEach
    void initStrongboxAndDepots() throws NegativeResourceAmountException,
            WrongDepotIndexException, SameResourceTypeInDifferentDepotsException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException {
        player1.getPersonalBoard().getWarehouseDepots().store(new StorableResource(ResourceType.SERVANT, 2), 1);
        player1.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.SERVANT, 8));

    }

    @Test
    void performFromStrongboxTest() throws Exception {
        ArrayList<PayAction> fromWhereAndWhat = new ArrayList<>();
        PayAction payAction1 = new StrongboxAction("remove", new StorableResource(ResourceType.SERVANT, 2));
        fromWhereAndWhat.add(payAction1);
        BuyCardAction buyCardAction = new BuyCardAction(2, 0, fromWhereAndWhat, 1);
        buyCardAction.perform(game, player1);
        ArrayList<DevelopmentCard> allDevelopmentCardsFromSlots = player1.getPersonalBoard().getAllDevelopmentCards();
        if(allDevelopmentCardsFromSlots.isEmpty())
            fail();
        //TODO: NOTHING... but the following assertTrue is incorrect... if i want to have a realistic development cards grid in tests this
        // instruction doesn't work, because the cards aren't equals, they have only the same colour and level, but not
        // cost, consumed resources and produced resources.
        // We are comparing two different cards because the buy action also removes the card from the grid, but we can
        // compare them because they have same colour and level
        assertTrue(allDevelopmentCardsFromSlots.get(0).equals(game.getGameBoard().getDevelopmentCardGrid().getChoosenCard(2, 0, player1)));
        ArrayList<StorableResource> resourcesFromStrongbox = player1.getPersonalBoard().getStrongbox().getAllResources();
        ArrayList<StorableResource> resourcesFromWarehouse = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertTrue(resourcesFromStrongbox.get(0).equals(new StorableResource(ResourceType.SERVANT, 6)));
        assertTrue(!resourcesFromWarehouse.isEmpty());
    }

    @Test
    void performFromWarehouseTest() throws Exception {
        ArrayList<PayAction> fromWhereAndWhat = new ArrayList<>();
        PayAction payAction1 = new WarehouseAction("remove", new StorableResource(ResourceType.SERVANT, 2), 1);
        fromWhereAndWhat.add(payAction1);
        BuyCardAction buyCardAction = new BuyCardAction(2, 0, fromWhereAndWhat, 1);
        buyCardAction.perform(game, player1);
        ArrayList<DevelopmentCard> allDevelopmentCardsFromSlots = player1.getPersonalBoard().getAllDevelopmentCards();
        if(allDevelopmentCardsFromSlots.isEmpty())
            fail();
        //TODO: NOTHING... but the following assertTrue is incorrect... if i want to have a realistic development cards grid in tests this
        // instruction doesn't work, because the cards aren't equals, they have only the same colour and level, but not
        // cost, consumed resources and produced resources.
        // We are comparing two different cards because the buy action also removes the card from the grid, but we can
        // compare them because they have same colour and level
        assertTrue(allDevelopmentCardsFromSlots.get(0).equals(game.getGameBoard().getDevelopmentCardGrid().getChoosenCard(2, 0, player1)));
        ArrayList<StorableResource> resourcesFromStrongbox = player1.getPersonalBoard().getStrongbox().getAllResources();
        ArrayList<StorableResource> resourcesFromWarehouse = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertTrue(!resourcesFromStrongbox.isEmpty());
        assertTrue(resourcesFromWarehouse.isEmpty());
    }

    @Test
    void performTooManyCards() throws Exception {
        ArrayList<PayAction> fromWhereAndWhat = new ArrayList<>();
        PayAction payAction1 = new StrongboxAction("remove", new StorableResource(ResourceType.SERVANT, 2));
        fromWhereAndWhat.add(payAction1);
        BuyCardAction buyCardAction = new BuyCardAction(2, 0, fromWhereAndWhat, 1);
        BuyCardAction buyCardAction1 = new BuyCardAction(1, 0, fromWhereAndWhat, 1);
        BuyCardAction buyCardAction2 = new BuyCardAction(0, 0, fromWhereAndWhat, 1);
        BuyCardAction buyCardAction3 = new BuyCardAction(0, 1, fromWhereAndWhat, 1);
        buyCardAction.perform(game, player1);
        buyCardAction1.perform(game, player1);
        buyCardAction2.perform(game, player1);
        ArrayList<DevelopmentCard> allDevelopmentCardsFromSlots = player1.getPersonalBoard().getAllDevelopmentCards();
        if(allDevelopmentCardsFromSlots.size() != 3)
            fail();
        try{
            buyCardAction3.perform(game, player1);
            fail();
        }
        catch(Exception e){
            assertTrue(true);
        }
    }
}