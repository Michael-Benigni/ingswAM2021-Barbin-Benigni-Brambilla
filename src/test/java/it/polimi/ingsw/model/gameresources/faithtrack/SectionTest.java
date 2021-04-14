package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.CellNotFoundInSectionException;
import it.polimi.ingsw.exception.LastCellInSectionException;
import it.polimi.ingsw.exception.WrongCellIndexException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on "Section" class, but it is an abstract class. So in this case we will use instances of "ClassicSection" class to test the methods of "Section".
 */
public class SectionTest {


    /**
     * Test on "getCell" method of this class.
     * Tests if the method returns the correct cell of the section.
     * @throws WrongCellIndexException -> can be thrown by "getCell" method "Section" class.
     */
    @Test
    void checkGetCellIfCorrect() throws WrongCellIndexException {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new ClassicCell();
        Cell cell2 = new PopeSpace();
        Cell cell3 = new VPCell(45);
        Cell cell4 = new ClassicCell();
        Cell cell5 = new VictoryPointsPopeSpace(12);

        listOfCell.add(cell1);
        listOfCell.add(cell2);
        listOfCell.add(cell3);
        listOfCell.add(cell4);
        listOfCell.add(cell5);

        Section newSection = new ClassicSection(listOfCell);

        assertEquals(newSection.getCell(4), cell5);
        assertEquals(newSection.getCell(3), cell4);
        assertEquals(newSection.getCell(2), cell3);
        assertEquals(newSection.getCell(1), cell2);
        assertEquals(newSection.getCell(0), cell1);
    }


    /**
     * Test on "getCell" method of this class.
     * Tests if the method throws successfully the exception when the provided index is less than zero or exceeds
     * the size of the section.
     * @throws WrongCellIndexException -> can be thrown in the catch clause by the "getCell" method.
     */
    @Test
    void checkGetCellIfWrongIndex() throws WrongCellIndexException {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell newCell = new ClassicCell();
        listOfCell.add(newCell);
        Section newSection = new ClassicSection(listOfCell);
        try{
            newSection.getCell(-3);
            fail();
        } catch (WrongCellIndexException e) {
            assertEquals(newSection.getCell(0), newCell);
        }
        try{
            newSection.getCell(14);
            fail();
        } catch (WrongCellIndexException e) {
            assertEquals(newSection.getCell(0), newCell);
        }
    }


    /**
     * Test on "searchNextCellInSection" method of this class.
     * Tests if the method return the next cell to the one provided.
     */
    @Test
    void checkSearchNextCellInSectionIfCorrect() {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new ClassicCell();
        Cell cell2 = new PopeSpace();
        listOfCell.add(cell1);
        listOfCell.add(cell2);
        Section newSection = new ClassicSection(listOfCell);
        try {
            Cell cell3 = newSection.searchNextCellInSection(cell1);
            assertEquals(cell3, cell2);
        } catch (CellNotFoundInSectionException e) {
            fail();
        } catch (LastCellInSectionException e) {
            fail();
        }
    }


    /**
     * Test on "searchNextCellInSection" method of this class.
     * Tests if the method recognizes if the provided cell is the last cell of the section and throws the correct exception.
     * @throws WrongCellIndexException -> can be thrown by "getCell" method of "Section" class.
     */
    @Test
    void checkSearchNextCellInSectionIfLastCell() throws WrongCellIndexException {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new VPCell(45);
        Cell cell2 = new VictoryPointsPopeSpace(10);
        listOfCell.add(cell1);
        listOfCell.add(cell2);
        Section newSection = new ClassicSection(listOfCell);
        try{
            newSection.searchNextCellInSection(cell2);
            fail();
        } catch (CellNotFoundInSectionException e) {
            fail();
        } catch (LastCellInSectionException e) {
            assertEquals(newSection.getCell(0), cell1);
            assertEquals(newSection.getCell(1), cell2);
        }
    }


    /**
     * Test on "searchNextCellInSection" method of this class.
     * Tests if the method throws the correct exception when the provided cell isn't in the section.
     * @throws WrongCellIndexException -> can be thrown by "getCell" method of "Section" class.
     */
    @Test
    void checkSearchNextCellInSectionIfNotFound() throws WrongCellIndexException {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new VPCell(7);
        Cell cell2 = new ClassicCell();
        Cell cell3 = new PopeSpace();
        listOfCell.add(cell1);
        listOfCell.add(cell2);
        Section newSection = new ClassicSection(listOfCell);
        try {
            newSection.searchNextCellInSection(cell3);
            fail();
        } catch (CellNotFoundInSectionException e) {
            assertEquals(newSection.getCell(0), cell1);
            assertEquals(newSection.getCell(1), cell2);
        } catch (LastCellInSectionException e) {
            fail();
        }
    }


    /**
     * Test on "firstCellInSection" method of this class.
     * Tests if the method returns correctly the first cell in the section.
     * @throws WrongCellIndexException -> can be thrown by "firstCellInSection" method of "Section" class.
     */
    @Test
    void checkFirstCellInSectionIfCorrect() throws WrongCellIndexException {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new ClassicCell();
        Cell cell2 = new VictoryPointsPopeSpace(8);
        listOfCell.add(cell1);
        listOfCell.add(cell2);
        Section newSection = new ClassicSection(listOfCell);
        Cell firstCell = newSection.firstCellInSection();
        assertEquals(firstCell, cell1);
    }


    /**
     * Test on "searchInThisSection" method of this class.
     * Tests if the method works correctly.
     */
    @Test
    void checkSearchInThisSectionIfCorrect() {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new PopeSpace();
        Cell cell2 = new ClassicCell();
        listOfCell.add(cell1);
        listOfCell.add(cell2);
        Section newSection = new ClassicSection(listOfCell);
        try {
            assertTrue(newSection.searchInThisSection(cell1));
            assertTrue(newSection.searchInThisSection(cell2));
        } catch (CellNotFoundInSectionException e) {
            fail();
        }
    }


    /**
     * Test on "searchInThisSection" method of this class.
     * Tests if the method throws successfully the exception when the provided cell isn't in the section.
     * @throws WrongCellIndexException -> can be thrown by "getCell" method of "Section" class.
     */
    @Test
    void checkSearchInThisSectionIfNotFound() throws WrongCellIndexException {
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        Cell cell1 = new VPCell(13);
        Cell cell2 = new VictoryPointsPopeSpace(21);
        Cell cell3 = new ClassicCell();
        listOfCell.add(cell1);
        listOfCell.add(cell2);
        Section newSection = new ClassicSection(listOfCell);
        try{
            boolean ifFound = newSection.searchInThisSection(cell3);
            fail();
        } catch (CellNotFoundInSectionException e) {
            assertEquals(newSection.getCell(0), cell1);
            assertEquals(newSection.getCell(1), cell2);
        }
    }
}
