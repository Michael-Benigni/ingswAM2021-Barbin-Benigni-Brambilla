package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.model.gameresources.markettray.Resource;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


/**
 * this is the test class for SlotDevelopmentCards
 */
class SlotDevelopmentCardsTest {

    /**
     * this test makes sure that the method getTopCard throws the EmptySlotException
     * @throws EmptySlotException
     */
    @Test
    void getTopCardTestException() throws Exception {
        SlotDevelopmentCards slotDevelopmentCards = new SlotDevelopmentCards();
        DevelopmentCard card;
        try {
            card = slotDevelopmentCards.getTopCard();
        }
        catch (EmptySlotException e) {
            assertTrue(true);
        }
    }

    /**
     * this test makes sure that the method getAllCards throws the EmptySlotException
     * @throws EmptySlotException
     */
    @Test
    void getAllCardsTestException() throws Exception {
        SlotDevelopmentCards slotDevelopmentCards = new SlotDevelopmentCards();
        ArrayList <DevelopmentCard> card;
        try {
            card = slotDevelopmentCards.getAllCards();
        }
        catch (EmptySlotException e) {
            assertTrue(true);
        }
    }

    //TODO: missing test for the exception SlotDevelopmentCardsIsFullException in method placeOnTop
    //TODO: the tests below don't work because we can't build the development card entity
    @Test
    void getTopCardTest() throws Exception {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList<StorableResource> cost = new ArrayList <StorableResource> (2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Resource> producedResources = new ArrayList <Resource> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        DevelopmentCard firstAddedCard = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        DevelopmentCard topCard = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, cost, consumedResources, producedResources);
        DevelopmentCard middleCard = new DevelopmentCard(CardColour.GREEN, CardLevel.THREE, cost, consumedResources, producedResources);
        SlotDevelopmentCards slot = new SlotDevelopmentCards();
        slot.placeOnTop(firstAddedCard);
        slot.placeOnTop(middleCard);
        slot.placeOnTop(topCard);
        assertTrue(topCard.equals(slot.getTopCard()));
    }


    @Test
    void getAllCardsTest() throws Exception {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList<StorableResource> cost = new ArrayList <StorableResource> (2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Resource> producedResources = new ArrayList <Resource> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        DevelopmentCard firstAddedCard = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        DevelopmentCard topCard = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, cost, consumedResources, producedResources);
        DevelopmentCard middleCard = new DevelopmentCard(CardColour.GREEN, CardLevel.THREE, cost, consumedResources, producedResources);
        SlotDevelopmentCards slot = new SlotDevelopmentCards();
        slot.placeOnTop(firstAddedCard);
        slot.placeOnTop(middleCard);
        slot.placeOnTop(topCard);
        ArrayList <DevelopmentCard> listOfAllAddedCards = new ArrayList <DevelopmentCard> (0);
        listOfAllAddedCards.add(firstAddedCard);
        listOfAllAddedCards.add(middleCard);
        listOfAllAddedCards.add(topCard);
        ArrayList <DevelopmentCard> listOfAllAddedCardsFromSlot = new ArrayList <DevelopmentCard> (0);
        listOfAllAddedCardsFromSlot = slot.getAllCards();
        if(listOfAllAddedCardsFromSlot.size() != listOfAllAddedCards.size())
            fail();
        for(int i = 0; i < listOfAllAddedCardsFromSlot.size(); i++) {
            if(!(listOfAllAddedCards.get(i).equals(listOfAllAddedCardsFromSlot.get(i)))) {
                fail();
            }
        }
    }


    @Test
    void placeOnTopTest() throws Exception {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList<StorableResource> cost = new ArrayList <StorableResource> (2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Resource> producedResources = new ArrayList <Resource> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        DevelopmentCard cardToAdd = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        SlotDevelopmentCards slot = new SlotDevelopmentCards();
        slot.placeOnTop(cardToAdd);
        assertTrue(cardToAdd.equals(slot.getTopCard()));
    }
}