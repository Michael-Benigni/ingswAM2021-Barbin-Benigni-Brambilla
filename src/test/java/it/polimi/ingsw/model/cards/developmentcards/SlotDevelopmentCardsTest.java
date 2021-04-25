package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.model.gameresources.Producible;
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
     * this test makes sure
     * that the method getTopCard
     * throws the EmptySlotException
     */
    @Test
    void getTopCardTestException() {
        SlotDevelopmentCards slotDevelopmentCards = new SlotDevelopmentCards(3);
        DevelopmentCard card;
        try {
            card = slotDevelopmentCards.getTopCard();
        }
        catch (EmptySlotException e) {
            assertTrue(true);
        }
    }

    /**
     * this test makes sure that
     * the method getAllCards
     * throws the EmptySlotException
     */
    @Test
    void getAllCardsTestException() {
        SlotDevelopmentCards slotDevelopmentCards = new SlotDevelopmentCards(3);
        try {
            slotDevelopmentCards.getAllCards();
        }
        catch (EmptySlotException e) {
            assertTrue(true);
        }
    }

    /**
     * this method checks if
     * the method place on top
     * throws the exception
     * DevelopmentCardNotAddableException
     * @throws NegativeResourceAmountException
     * @throws DevelopmentCardNotAddableException
     * @throws SlotDevelopmentCardsIsFullException
     */
    @Test
    void placeOnTopTestCardNotAddableException() throws Exception {
        DevelopmentCard cardToAdd = buildCardsForTests().get(1);
        SlotDevelopmentCards slot = new SlotDevelopmentCards(3);
        try {
            slot.placeOnTop(cardToAdd);
        }
        catch(DevelopmentCardNotAddableException e) {
            assertTrue(true);
        }
    }

    /**
     * this method checks if
     * the method place on top
     * throws the exception
     * SlotDevelopmentCardsIsFullException
     * @throws NegativeResourceAmountException
     * @throws DevelopmentCardNotAddableException
     * @throws SlotDevelopmentCardsIsFullException
     */
    @Test
    void placeOnTopTestFullSlotException() throws Exception {
        ArrayList <DevelopmentCard> cardsList = buildCardsForTests();
        SlotDevelopmentCards slot = new SlotDevelopmentCards(3);
        for(int i = 0; i < cardsList.size(); i++) {
            slot.placeOnTop(cardsList.get(i));
        }
        try {
            slot.placeOnTop(cardsList.get(0));
        }
        catch(SlotDevelopmentCardsIsFullException e) {
            assertTrue(true);
        }
    }

    /**
     * this method tests the method getTopCard
     * @throws NegativeResourceAmountException
     * @throws SlotDevelopmentCardsIsFullException
     * @throws DevelopmentCardNotAddableException
     * @throws EmptySlotException
     */
    @Test
    void getTopCardTest() throws Exception {
        ArrayList <DevelopmentCard> cardsList = buildCardsForTests();
        SlotDevelopmentCards slot = new SlotDevelopmentCards(3);
        for(int i = 0; i < cardsList.size(); i++) {
            slot.placeOnTop(cardsList.get(i));
        }
        assertTrue(cardsList.get(cardsList.size()-1).equals(slot.getTopCard()));
    }

    /**
     * this method tests the method getAllCards
     * @throws NegativeResourceAmountException
     * @throws SlotDevelopmentCardsIsFullException
     * @throws DevelopmentCardNotAddableException
     * @throws EmptySlotException
     */
    @Test
    void getAllCardsTest() throws Exception {
        ArrayList <DevelopmentCard> listOfAllAddedCards = buildCardsForTests();
        SlotDevelopmentCards slot = new SlotDevelopmentCards(3);
        for(int i = 0; i < listOfAllAddedCards.size(); i++) {
            slot.placeOnTop(listOfAllAddedCards.get(i));
        }
        ArrayList <DevelopmentCard> listOfAllAddedCardsFromSlot = slot.getAllCards();
        if(listOfAllAddedCardsFromSlot.size() != listOfAllAddedCards.size())
            fail();
        for(int i = 0; i < listOfAllAddedCardsFromSlot.size(); i++) {
            if(!(listOfAllAddedCards.get(i).equals(listOfAllAddedCardsFromSlot.get(i)))) {
                fail();
            }
        }
    }

    /**
     * this method tests the method
     * placeOnTop to be sure that the
     * card is added to the slot correctly
     * @throws NegativeResourceAmountException
     * @throws EmptySlotException
     * @throws SlotDevelopmentCardsIsFullException
     * @throws DevelopmentCardNotAddableException
     */
    @Test
    void placeOnTopTest() throws Exception {
        DevelopmentCard cardToAdd = buildCardsForTests().get(0);
        SlotDevelopmentCards slot = new SlotDevelopmentCards(3);
        slot.placeOnTop(cardToAdd);
        assertTrue(cardToAdd.equals(slot.getTopCard()));
    }

    /**
     * this method creates a list
     * of 3 development cards
     * to be used in tests
     * @return the created list of development cards
     * @throws NegativeResourceAmountException
     */
    private ArrayList <DevelopmentCard> buildCardsForTests() throws NegativeResourceAmountException, NegativeVPAmountException {
        ArrayList <DevelopmentCard> listOfCards = new ArrayList<>(0);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList<StorableResource> cost = new ArrayList <StorableResource> (2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Producible> producedResources = new ArrayList <Producible> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        VictoryPoint victoryPoints = new VictoryPoint(4);
        DevelopmentCard firstAddedCard = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard middleCard = new DevelopmentCard(CardColour.GREEN, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard topCard = new DevelopmentCard(CardColour.BLUE, CardLevel.THREE, cost, consumedResources, producedResources, victoryPoints);
        listOfCards.add(firstAddedCard);
        listOfCards.add(middleCard);
        listOfCards.add(topCard);
        return listOfCards;
    }
}