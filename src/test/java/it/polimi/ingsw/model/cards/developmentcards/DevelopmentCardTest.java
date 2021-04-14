package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.gameresources.markettray.Resource;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
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
    void cloneTest() throws NegativeResourceAmountException, NegativeVPAmountException {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList <StorableResource> cost = new ArrayList<StorableResource>(2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Resource> producedResources = new ArrayList <Resource> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        VictoryPoint victoryPoints = new VictoryPoint(3);
        producedResources.add(shield);
        producedResources.add(servant);
        DevelopmentCard card = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard cardCopy;
        cardCopy = (DevelopmentCard) card.clone();
        assertTrue(card.equals(cardCopy));
    }

    /**
     * this method tests the isEqual method
     * @throws NegativeResourceAmountException
     */
    @Test
    void equalsTest() throws NegativeResourceAmountException, NegativeVPAmountException {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList <StorableResource> cost = new ArrayList <StorableResource> (2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Resource> producedResources = new ArrayList <Resource> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        VictoryPoint victoryPoints = new VictoryPoint(4);
        DevelopmentCard cardOne = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard cardTwo = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        assertTrue(cardOne.equals(cardTwo));
    }
}