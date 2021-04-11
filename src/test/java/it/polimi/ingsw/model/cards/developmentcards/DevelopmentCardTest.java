package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
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
     * this method tests the createCopy method
     * @throws NegativeResourceAmountException
     */
    @Test
    void createCopyTest() throws NegativeResourceAmountException {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList <StorableResource> cost = new ArrayList<StorableResource>(2);
        ArrayList <StorableResource> consumedResources = new ArrayList <StorableResource> (1);
        ArrayList <Resource> producedResources = new ArrayList <Resource> (2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        DevelopmentCard card = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        DevelopmentCard cardCopy;
        cardCopy = card.clone();
        assertTrue(card.equals(cardCopy));
    }

    /**
     * this method tests the isEqual method
     * @throws NegativeResourceAmountException
     */
    @Test
    void isEqualTest() throws NegativeResourceAmountException {
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
        DevelopmentCard cardOne = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        DevelopmentCard cardTwo = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        assertTrue(cardOne.equals(cardTwo));
    }
}