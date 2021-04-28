package it.polimi.ingsw.model.gamelogic.actions;

import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.gamelogic.actions.PersonalBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.gameresources.stores.Strongbox;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tests on the "PersonalBoard" class.
 */
public class PersonalBoardTest {
    private static PersonalBoard p;

    @BeforeEach
    static void init() {
        ArrayList<Integer> capacities = new ArrayList<>(Arrays.asList(1, 2, 3));
        p = new PersonalBoard(new WarehouseDepots(3, capacities), 3, 3, 4);
    }

    @Test
    void getStrongbox() {
        Strongbox toTest = p.getStrongbox();
        assertNotNull(toTest);
        assertEquals(toTest, new Strongbox());
    }

    @Test
    void getWarehouseDepots() {
        WarehouseDepots toTest = p.getWarehouseDepots();
        assertNotNull(toTest);
        assertEquals(toTest, new WarehouseDepots(0, null));
    }

    @Test
    void getSlotDevelopmentCards() {
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
        SlotDevelopmentCards toTest = null;
        try {
            toTest = p.getSlotDevelopmentCards(-1);
        } catch (WrongSlotDevelopmentIndexException e) {
            assertTrue(true);
        }
        assertNull(toTest);
    }

    @Test
    void initFromJSON() throws WrongSlotDevelopmentIndexException, FileNotFoundException {
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
