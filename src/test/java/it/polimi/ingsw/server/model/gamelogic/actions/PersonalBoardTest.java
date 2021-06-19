package it.polimi.ingsw.server.model.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.server.model.cards.developmentcards.CardLevel;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.server.model.cards.leadercards.Requirement;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.server.model.gameresources.stores.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests on the "PersonalBoard" class.
 */
public class PersonalBoardTest {

    public static PersonalBoard init() {
        ArrayList<Integer> capacities = new ArrayList<>(Arrays.asList(1, 2, 3));
        int numOfResourcesForProduction = 2;
        int numOfResourcesToProduce = 1;
        return new PersonalBoard(numOfResourcesForProduction, numOfResourcesToProduce, new WarehouseDepots(3, capacities), 3, 3, 4, 2);
    }

    /**
     * Test on "getStrongbox" method of this class.
     * It tests if the method returns correctly an empty strongbox.
     */
    @Test
    void getStrongbox() {
        PersonalBoard p = PersonalBoardTest.init();
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
        PersonalBoard p = PersonalBoardTest.init();
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
        PersonalBoard p = PersonalBoardTest.init();
        SlotDevelopmentCards toTest = null;
        try {
            toTest = p.getSlotDevelopmentCards(1);
        } catch (WrongSlotDevelopmentIndexException e) {
            fail();
        }
        assertNotNull(toTest);
        assertEquals(toTest, new SlotDevelopmentCards(3, 1));
    }

    /**
     * Test on "getSlotDevelopmentCards" method of this class.
     * It tests if the method throws successfully the exception when the provided index is less than zero.
     */
    @Test
    void getSlotDevelopmentCardsThrowsExc() {
        PersonalBoard p = PersonalBoardTest.init();
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
        PersonalBoard p = PersonalBoardTest.init();
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
        PersonalBoard p = PersonalBoardTest.init();
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
     * Test on "getAllResource" method of this class.
     * It tests if the method successfully returns an empty arraylist when this personal board doesn't contain any resource.
     */
    @Test
    void getAllResourceWhenEmpty() {
        PersonalBoard p = PersonalBoardTest.init();
        assertEquals(p.getAllResource(), new ArrayList<>(0));
    }

    /**
     * Test on "getAllDevelopmentCards" method of this class.
     * It tests if the method returns the correct list of cards contained in its slots.
     * @throws Exception can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void getAllDevelopmentCards() throws Exception {
        PersonalBoard p = PersonalBoardTest.init();
        ArrayList<StorableResource> stone3 = new ArrayList<>(0);
        stone3.add(new StorableResource(ResourceType.STONE, 3));
        ArrayList<StorableResource> coin1 = new ArrayList<>(0);
        coin1.add(new StorableResource(ResourceType.COIN, 1));
        ArrayList<Producible> faithPoint2 = new ArrayList<>(0);
        faithPoint2.add(new FaithPoint(2));
        int cardID = 1;
        DevelopmentCard card1 = new DevelopmentCard(CardColour.BLUE, CardLevel.ONE, cardID, stone3, coin1, faithPoint2, new VictoryPoint(3));

        ArrayList<StorableResource> stone5 = new ArrayList<>(0);
        stone5.add(new StorableResource(ResourceType.STONE, 5));
        ArrayList<Producible> fp3shield1 = new ArrayList<>(0);
        fp3shield1.add(new FaithPoint(3));
        fp3shield1.add(new StorableResource(ResourceType.SHIELD, 1));
        DevelopmentCard card2 = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, cardID, stone5, coin1, fp3shield1, new VictoryPoint(6));

        ArrayList<StorableResource> servant4 = new ArrayList<>(0);
        servant4.add(new StorableResource(ResourceType.SERVANT, 4));
        ArrayList<StorableResource> stone1 = new ArrayList<>(0);
        stone1.add(new StorableResource(ResourceType.STONE, 1));
        ArrayList<Producible> coin3 = new ArrayList<>(0);
        coin3.add(new StorableResource(ResourceType.COIN, 3));
        DevelopmentCard card3 = new DevelopmentCard(CardColour.GREEN, CardLevel.ONE, cardID, servant4, stone1, coin3, new VictoryPoint(1));

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
     * Test on "getAllDevelopmentCards" method of this class.
     * It tests if the method successfully returns an empty arraylist when the slots are all empty.
     */
    @Test
    void getAllDevelopmentCardsWhenEmpty() {
        PersonalBoard p = PersonalBoardTest.init();
        assertEquals(p.getAllDevelopmentCards(), new ArrayList<>(0));
    }

    /**
     * Test on "addExtraProductionPower" method of this class.
     * It tests if the method add an extra production power to the personal board, then also checks if the
     * "produce" method of "PersonalBoard" class works correctly.
     * @throws NegativeResourceAmountException can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void addExtraProductionPower() throws NegativeResourceAmountException {
        PersonalBoard p = PersonalBoardTest.init();
        Player player = new Player();
        player.buildBoard(p);
        try {
            player.getPersonalBoard().getExtraPower(0);
            fail();
        } catch(NotExistingExtraProductionPower e) {
            assertTrue(true);
        }
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        p.addExtraProductionPower(coin, 1, 1);
        try {
            p.getExtraPower(0).produce(player, coin);
            fail ();
        } catch(NotExistingExtraProductionPower e) {
            fail();
        } catch (NotContainedResourceException e) {
            assertTrue  (true);
        } catch (InvalidAmountForExtraProductionProducedResource invalidAmountForExtraProductionProducedResource) {
            fail ();
        }
        player.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.COIN, 10));
        try {
            ArrayList<Producible> producibles = p.getExtraPower(0).produce(player, coin);
            assertTrue(producibles.size() == 2 && producibles.contains(coin) && producibles.contains(new FaithPoint(1)));
        } catch (NotExistingExtraProductionPower | NotContainedResourceException | InvalidAmountForExtraProductionProducedResource e) {
            fail();
        }
    }
}
