package it.polimi.ingsw.server.model.cards.developmentcards;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * test class of the DevelopmentCard class
 */
class DevelopmentCardTest {
    /**
     * this method tests the clone method
     * @throws NegativeResourceAmountException
     * @throws NegativeVPAmountException
     */
    @Test
    void cloneTest() throws NegativeResourceAmountException {
        DevelopmentCard card = buildCardForTests1();
        DevelopmentCard cardCopy;
        cardCopy = (DevelopmentCard) card.clone();
        assertTrue(card.equals(cardCopy));
    }

    /**
     * this method tests the isEqual method
     * @throws NegativeResourceAmountException
     */
    @Test
    void equalsTest() throws NegativeResourceAmountException {
        DevelopmentCard cardOne = buildCardForTests1();
        DevelopmentCard cardTwo = buildCardForTests1();
        assertTrue(cardOne.equals(cardTwo));
    }

    /**
     * this method tests the method reduceCost
     * @throws NegativeResourceAmountException
     */
    @Test
    void reduceCostTest() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.COIN, 3);
        ArrayList <StorableResource> listOfResource = new ArrayList<>(0);
        listOfResource.add(resource);
        ArrayList <StorableResource> listOfResource2 = new ArrayList<>(0);
        StorableResource resource2 = new StorableResource(ResourceType.COIN, 2);
        listOfResource2.add(resource2);
        ArrayList <Producible> list = new ArrayList<>(0);
        list.add(resource);
        VictoryPoint victoryPoint = new VictoryPoint(1);
        DevelopmentCard card = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, listOfResource, listOfResource, list, victoryPoint);
        StorableResource discount = new StorableResource(ResourceType.COIN, 1);
        DevelopmentCard card2 = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, listOfResource2, listOfResource, list, victoryPoint);
        card.reduceCost(discount);
        assertTrue(card.equals(card2));
    }

    @Test
    void hasSameLevelTest1() throws NegativeResourceAmountException {
        DevelopmentCard card1 = buildCardForTests1();
        DevelopmentCard card2 = buildCardForTests1();
        assertTrue(card1.levelCompare(card2) == 0);
    }

    @Test
    void hasSameLevelTest2() throws NegativeResourceAmountException {
        DevelopmentCard card1 = buildCardForTests1();
        DevelopmentCard card2 = buildCardForTests2();
        assertTrue(card1.levelCompare(card2) != 0);
    }

    @Test
    void hasSameColourTest1() throws NegativeResourceAmountException {
        DevelopmentCard card1 = buildCardForTests1();
        DevelopmentCard card2 = buildCardForTests1();
        assertTrue(card1.hasSameColour(card2));
    }

    @Test
    void hasSameColourTest2() throws NegativeResourceAmountException {
        DevelopmentCard card1 = buildCardForTests1();
        DevelopmentCard card2 = buildCardForTests2();
        assertTrue(!card1.hasSameColour(card2));
    }

    @Test
    void isOfNextLevelTest1() throws NegativeResourceAmountException {
        DevelopmentCard card1 = buildCardForTests1();
        DevelopmentCard card2 = buildCardForTests2();
        assertTrue(card2.isOfNextLevel(card1));
    }

    @Test
    void isOfNextLevelTest2() throws NegativeResourceAmountException {
        DevelopmentCard card1 = buildCardForTests1();
        DevelopmentCard card2 = buildCardForTests2();
        assertTrue(!card1.isOfNextLevel(card2));
    }

    DevelopmentCard buildCardForTests1() throws NegativeResourceAmountException {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList <StorableResource> cost = new ArrayList<StorableResource>(2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Producible> producedResources = new ArrayList <Producible> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        VictoryPoint victoryPoints = new VictoryPoint(3);
        producedResources.add(shield);
        producedResources.add(servant);
        return new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
    }

    /**
     * this method
     * @return
     * @throws NegativeResourceAmountException
     */
    DevelopmentCard buildCardForTests2() throws NegativeResourceAmountException {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList <StorableResource> cost = new ArrayList<StorableResource>(2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Producible> producedResources = new ArrayList <Producible> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        VictoryPoint victoryPoints = new VictoryPoint(3);
        producedResources.add(shield);
        producedResources.add(servant);
        return new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
    }
}