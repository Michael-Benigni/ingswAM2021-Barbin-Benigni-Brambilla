package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on "FaithMarker" class.
 */
public class FaithMarkerTest {

    /**
     * Test on the constructor method of this class.
     * It tests if the constructor method builds a correct object and if the method "getCurrentCell" of this class
     * returns the correct cell.
     */
    @Test
    void checkConstructorIfCorrect() {
        Cell newCell = new ClassicCell();
        FaithMarker faithMarker = new FaithMarker(newCell);
        assertEquals(faithMarker.getCurrentCell(), newCell);
    }


    /**
     * Test on "updateCurrentCell" method of this class.
     * It tests if the method update successfully the current cell in the faith marker.
     */
    @Test
    void checkUpdateCurrentCellIfCorrect() {
        Cell newCell = new ClassicCell();
        Cell newestCell = new VPCell(new VictoryPoint(14));
        FaithMarker faithMarker = new FaithMarker(newCell);
        faithMarker.updateCurrentCell(newestCell);
        assertEquals(newestCell, faithMarker.getCurrentCell());
    }


    /**
     * Test on "updateLastVictoryPoint" method of this class.
     * It tests if the method returns the correct object of "VictoryPoint" class, that corresponds to the difference
     * between the amount stored in the faith marker and the new amount provided.
     * @throws NegativeVPAmountException -> can be thrown by "updateLastVictoryPoint" method of this class.
     */
    @Test
    void checkUpdateLastVictoryPointIfCorrect() throws NegativeVPAmountException {
        Cell newCell = new ClassicCell();
        FaithMarker faithMarker = new FaithMarker(newCell);
        VictoryPoint vp1 = new VictoryPoint(6);
        VictoryPoint vp2 = new VictoryPoint(19);
        VictoryPoint difference = new VictoryPoint(19 - 6);
        assertEquals(faithMarker.updateLastVictoryPoint(vp1), vp1);
        assertEquals(faithMarker.updateLastVictoryPoint(vp2), difference);
    }


    /**
     * Test on "updateLastVictoryPoint" method of this class.
     * It tests if the method successfully throws the exception when the new victory point has amount less than the one
     * stored in the faith marker.
     * @throws NegativeVPAmountException -> can be thrown by "updateLastVictoryPoint" method of this class.
     */
    @Test
    void checkUpdateLastVictoryPointIfNegativeAmount() throws NegativeVPAmountException {
        Cell newCell = new ClassicCell();
        FaithMarker faithMarker = new FaithMarker(newCell);
        VictoryPoint vp1 = new VictoryPoint(100);
        VictoryPoint vp2 = new VictoryPoint(10);
        assertEquals(faithMarker.updateLastVictoryPoint(vp1), vp1);
        try {
            faithMarker.updateLastVictoryPoint(vp2);
            fail();
        } catch(NegativeVPAmountException e) {
            assertEquals(faithMarker.updateLastVictoryPoint(vp1), new VictoryPoint(0));
        }
    }


    /**
     * Test on "ifIsInThisSection" method of this class.
     * It tests if the method returns the correct answer in various cases.
     */
    @Test
    void checkIfIsInThisSectionIfCorrect() {
        ArrayList<Cell> listOfCells = new ArrayList<>(0);
        Cell cell1 = new VPCell(new VictoryPoint(10));
        Cell cell2 = new ClassicCell();
        Cell cell3 = new VPCell(new VictoryPoint(14));
        Cell cellExcluded = new VPCell(new VictoryPoint(145));
        listOfCells.add(cell1);
        listOfCells.add(cell2);
        listOfCells.add(cell3);
        Section newSection = new ClassicSection(listOfCells);
        FaithMarker faithMarker = new FaithMarker(cell1);
        assertTrue(faithMarker.ifIsInThisSection(newSection));
        faithMarker.updateCurrentCell(cell2);
        assertTrue(faithMarker.ifIsInThisSection(newSection));
        faithMarker.updateCurrentCell(cellExcluded);
        assertFalse(faithMarker.ifIsInThisSection(newSection));
    }
}
