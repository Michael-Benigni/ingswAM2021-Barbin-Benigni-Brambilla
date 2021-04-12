package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.gameresources.stores.Strongbox;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests on the "PersonalBoard" class.
 */
public class PersonalBoardTest {

    @Test
    void getStrongbox() {
        PersonalBoard p = new PersonalBoard(3, 2);
        Strongbox toTest = p.getStrongbox();
        assertNotNull(toTest);
        assertEquals(toTest, new Strongbox());
    }

    @Test
    void getWarehouseDepots() {
        PersonalBoard p = new PersonalBoard(3, 2);
        WarehouseDepots toTest = p.getWarehouseDepots();
        assertNotNull(toTest);
        assertEquals(toTest, new WarehouseDepots(0, null));
    }

    @Test
    void getSlotDevelopmentCards() {
        PersonalBoard p = new PersonalBoard(3, 2);
        SlotDevelopmentCards toTest = null;
        try {
            toTest = p.getSlotDevelopmentCards(1);
        } catch (WrongSlotDevelopmentIndexException e) {
            fail();
        }
        assertNotNull(toTest);
        assertEquals(toTest, new SlotDevelopmentCards(0));
    }

    @Test
    void getSlotDevelopmentCardsThrowsExc() {
        PersonalBoard p = new PersonalBoard(3, 2);
        SlotDevelopmentCards toTest = null;
        try {
            toTest = p.getSlotDevelopmentCards(-1);
        } catch (WrongSlotDevelopmentIndexException e) {
            assertTrue(true);
        }
        assertNull(toTest);
    }

    @Test
    void initFromJSON() throws FileNotFoundException, WrongSlotDevelopmentIndexException {
        PersonalBoard p = new PersonalBoard(3, 2).initFromJSON();
        WarehouseDepots warehouseDepots = p.getWarehouseDepots();
        assertNotNull(warehouseDepots);
        Integer [] capacities = {1, 2, 3};
        assertEquals(warehouseDepots, new WarehouseDepots(3, new ArrayList<Integer>(new ArrayList<Integer>(Arrays.asList(capacities)))));
        Strongbox strongbox = p.getStrongbox();
        assertNotNull(strongbox);
        assertEquals(strongbox, new Strongbox());
        SlotDevelopmentCards slotDevelopmentCards = p.getSlotDevelopmentCards(1);
        assertNotNull(slotDevelopmentCards);
        assertEquals(slotDevelopmentCards, new SlotDevelopmentCards(3));
    }
}
