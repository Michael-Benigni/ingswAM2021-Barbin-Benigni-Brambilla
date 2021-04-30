package it.polimi.ingsw.model.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotExistingExtraProductionPower;
import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.*;
import it.polimi.ingsw.model.cards.leadercards.Requirement;
import it.polimi.ingsw.model.gameresources.Producible;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.stores.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests on the "PersonalBoard" class.
 */
public class PersonalBoardTest {
    private static PersonalBoard p;

    @BeforeEach
    void init() {
        ArrayList<Integer> capacities = new ArrayList<>(Arrays.asList(1, 2, 3));
        p = new PersonalBoard(new WarehouseDepots(3, capacities), 3, 3, 4);
    }

    /**
     * Test on "getStrongbox" method of this class.
     * It tests if the method returns correctly an empty strongbox.
     */
    @Test
    void getStrongbox() {
        Strongbox toTest = p.getStrongbox();
        assertNotNull(toTest);
        assertEquals(toTest, new Strongbox());
    }

    /**
     * Test on "getWarehouseDepots" method of this class.
     * It tests if the method returns exactly the warehouse initialized in the "init()" method of this test class.
     */
    @Test
    void getWarehouseDepots() {
        WarehouseDepots toTest = p.getWarehouseDepots();
        assertNotNull(toTest);
        ArrayList<Integer> capacities = new ArrayList<>(Arrays.asList(1, 2, 3));
        assertEquals(toTest, new WarehouseDepots(3, capacities));
    }

    /**
     * Test on "getSlotDevelopmentCards" method of this class.
     * It tests if the method returns exactly the slot initialized in the "init()" method of this test class.
     */
    @Test
    void getSlotDevelopmentCards() {
        SlotDevelopmentCards toTest = null;
        try {
            toTest = p.getSlotDevelopmentCards(1);
        } catch (WrongSlotDevelopmentIndexException e) {
            fail();
        }
        assertNotNull(toTest);
        assertEquals(toTest, new SlotDevelopmentCards(3));
    }

    /**
     * Test on "getSlotDevelopmentCards" method of this class.
     * It tests if the method throws successfully the exception when the provided index is less than zero.
     */
    @Test
    void getSlotDevelopmentCardsThrowsExc() {
        SlotDevelopmentCards toTest = null;
        try {
            toTest = p.getSlotDevelopmentCards(-1);
            fail();
        } catch (WrongSlotDevelopmentIndexException e) {
            assertTrue(true);
        }
        assertNull(toTest);
    }

    /**
     * Test on "getTempContainer" method of this class.
     * It tests if the method returns an empty temporary container.
     */
    @Test
    void getTempContainer() {
        TemporaryContainer temporaryContainer = p.getTempContainer();
        assertNotNull(temporaryContainer);
        assertEquals(temporaryContainer, new TemporaryContainer());
    }

    /**
     * Test on "getAllResource" method of this class.
     * It tests if the method returns the entire list of requirements contained in the strongbox and in the warehouse depots.
     * @throws Exception can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void getAllResource() throws Exception {
        StorableResource coin = new StorableResource(ResourceType.COIN, 3);
        StorableResource stone = new StorableResource(ResourceType.STONE, 7);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        p.getStrongbox().store(stone);
        p.getWarehouseDepots().store(servant, 0);
        p.getWarehouseDepots().store(coin, 2);
        ArrayList<Requirement> listToBeCompared = new ArrayList<>(0);
        listToBeCompared.add(stone);
        listToBeCompared.add(servant);
        listToBeCompared.add(coin);
        assertEquals(p.getAllResource(), listToBeCompared);
    }

    /**
     * Test on "getAllDevelopmentCards" method of this class.
     * It tests if the method returns the correct list of cards contained in its slots.
     * @throws Exception can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void getAllDevelopmentCards() throws Exception {
        ArrayList<StorableResource> stone3 = new ArrayList<>(0);
        stone3.add(new StorableResource(ResourceType.STONE, 3));
        ArrayList<StorableResource> coin1 = new ArrayList<>(0);
        coin1.add(new StorableResource(ResourceType.COIN, 1));
        ArrayList<Producible> faithPoint2 = new ArrayList<>(0);
        faithPoint2.add(new FaithPoint(2));
        DevelopmentCard card1 = new DevelopmentCard(CardColour.BLUE, CardLevel.ONE, stone3, coin1, faithPoint2, new VictoryPoint(3));

        ArrayList<StorableResource> stone5 = new ArrayList<>(0);
        stone5.add(new StorableResource(ResourceType.STONE, 5));
        ArrayList<Producible> fp3shield1 = new ArrayList<>(0);
        fp3shield1.add(new FaithPoint(3));
        fp3shield1.add(new StorableResource(ResourceType.SHIELD, 1));
        DevelopmentCard card2 = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, stone5, coin1, fp3shield1, new VictoryPoint(6));

        ArrayList<StorableResource> servant4 = new ArrayList<>(0);
        servant4.add(new StorableResource(ResourceType.SERVANT, 4));
        ArrayList<StorableResource> stone1 = new ArrayList<>(0);
        stone1.add(new StorableResource(ResourceType.STONE, 1));
        ArrayList<Producible> coin3 = new ArrayList<>(0);
        coin3.add(new StorableResource(ResourceType.COIN, 3));
        DevelopmentCard card3 = new DevelopmentCard(CardColour.GREEN, CardLevel.ONE, servant4, stone1, coin3, new VictoryPoint(1));

        ArrayList<DevelopmentCard> listOfCards = new ArrayList<>(0);
        listOfCards.add(card1);
        listOfCards.add(card2);
        listOfCards.add(card3);

        p.getSlotDevelopmentCards(0).placeOnTop(card1);
        p.getSlotDevelopmentCards(0).placeOnTop(card2);
        p.getSlotDevelopmentCards(1).placeOnTop(card3);
        assertEquals(p.getAllDevelopmentCards(), listOfCards);
    }

    /**
     * Test on "addExtraProductionPower" method of this class.
     * It tests if the method add an extra production power to the personal board, then also checks if the
     * "checkExtraPower" method of "PersonalBoard" class works correctly.
     * @throws NegativeResourceAmountException can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void addExtraProductionPower() throws NegativeResourceAmountException {
        Player player = new Player();
        player.buildBoard(p);
        try {
            player.getPersonalBoard().checkExtraPower(0, player);
            fail();
        } catch(NotExistingExtraProductionPower e) {
            assertTrue(true);
        }
        StorableResource coin = new StorableResource(ResourceType.COIN, 6);
        p.addExtraProductionPower(coin);
        try {
            assertFalse(p.checkExtraPower(0, player));
            player.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.COIN, 10));
            assertTrue(p.checkExtraPower(0, player));
        } catch(NotExistingExtraProductionPower e) {
            fail();
        }
    }

    /**
     * Test on "activateExtraPowerProduction" method of this class.
     * It tests if the method returns an arraylist that contains the provided storable resource and one faith point
     * with amount equal to one.
     * @throws NegativeResourceAmountException can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void activateExtraPowerProduction() throws NegativeResourceAmountException {
        ArrayList<Producible> list = new ArrayList<>(0);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 9);
        list.add(servant);
        list.add(new FaithPoint(1));
        assertEquals(p.activateExtraProductionPower(servant), list);
    }
}
