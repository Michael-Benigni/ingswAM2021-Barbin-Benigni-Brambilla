package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.*;

import java.util.ArrayList;

/**
 * Abstract class that represents a section of the faith track. A section can be a classic one or a vatican report one.
 */
public abstract class Section {

    private final ArrayList<Cell> listCell;

    /**
     * Constructor method of this class.
     * @param listCell -> array of cells.
     */
    public Section(ArrayList<Cell> listCell) {
        this.listCell = listCell;
    }


    /**
     * Method that returns the cell in the position determined by cellIndex.
     * @param cellIndex -> position of the cell you want to obtain.
     * @return -> the cell in cellIndex position.
     * @throws WrongCellIndexException -> exception thrown if the cellIndex is less than zero or greater than the
     * size of this section.
     */
    Cell getCell(int cellIndex) throws WrongCellIndexException {
        if(cellIndex < 0 || cellIndex >= listCell.size())
            throw new WrongCellIndexException();
        else
            return this.listCell.get(cellIndex);
    }


    /**
     * Method that returns the next cell of the one provided.
     * @param currentCell -> cell in input.
     * @return -> the next cell if it exists.
     * @throws LastCellInSectionException -> exception thrown if the method finds the cell but it's the last of this
     * section, so it can't return the next one.
     * @throws CellNotFoundInSectionException -> exception thrown if the cell isn't in this section.
     */
    Cell searchNextCellInSection(Cell currentCell) throws Exception {
        for(int i = 0; i < listCell.size(); i++) {
            if(listCell.get(i) == currentCell) {
                if( listCell.get(i) == lastCellInSection()) {
                    throw new LastCellInSectionException();
                }
                else {
                    return listCell.get(i + 1);
                }
            }
        }
        throw new CellNotFoundInSectionException();
    }


    /**
     * Method that returns the first cell of this section.
     * @return -> the first cell of this section.
     */
    Cell firstCellInSection() throws WrongCellIndexException {
        return getCell(0);
    }


    /**
     * Method that returns the last cell of this section.
     * @return -> the last cell of this section.
     */
    Cell lastCellInSection() {
        return listCell.get(listCell.size() - 1);
    }


    /**
     * Method that throws an exception if the provided cell isn't a cell of this section.
     * @param currentCell -> cell to be found in this section
     * @throws CellNotFoundInSectionException
     */
    boolean searchInThisSection(Cell currentCell) throws CellNotFoundInSectionException {
        for(Cell c : listCell) {
            if(c == currentCell)
                return true;
        }
        throw new CellNotFoundInSectionException();
    }

}
