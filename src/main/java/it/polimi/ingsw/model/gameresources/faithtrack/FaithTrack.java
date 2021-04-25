package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamelogic.actions.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents the common faith track on the game board. Every player has a marker that indicates its position on the track.
 */
public class FaithTrack {

    private ArrayList<Section> listOfSection;
    private HashMap<Player, FaithMarker> mapOfFaithMarker;


    /**
     * Constructor method of this class. It reads from a json file the entire structure of the track.
     */
    public FaithTrack(ArrayList<Section> arrayOfSections, ArrayList<Player> listOfPlayers) throws WrongCellIndexException, NegativeVPAmountException {
        this.listOfSection = new ArrayList<>(0);
        this.listOfSection = arrayOfSections;
        this.mapOfFaithMarker = new HashMap<>(0);
        Cell firstCell = firstCellInFaithTrack();
        for(Player p : listOfPlayers) {
            FaithMarker temporaryFaithMarker = new FaithMarker(firstCell);
            this.mapOfFaithMarker.put(p, temporaryFaithMarker);
        }
    }


    /**
     * Method that returns the first cell of the track to the caller.
     *
     * @return -> the first cell of the track.
     */
    private Cell firstCellInFaithTrack() throws WrongCellIndexException {
        return listOfSection.get(0).getCell(0);
    }


    /**
     * Method that update the provided cell with the next on the track.
     * @param currentCell -> the current cell, so the caller wants the next one.
     * @return -> the next cell.
     */
    private Cell nextCell(Cell currentCell) throws Exception {
        for (Section s : listOfSection) {
            try {
                Cell nextCell = s.searchNextCellInSection(currentCell);
                return nextCell;
            } catch (LastCellInSectionException e) {
                int sectionIndex = listOfSection.indexOf(s) + 1;
                return listOfSection.get(sectionIndex).firstCellInSection();
            }
        }
        throw new CellNotFoundInFaithTrackException();
    }


    /**
     * Method that return the section that contains the provided cell.
     * @param currentCell -> cell to find in the faith track.
     * @return -> the section if exists.
     * @throws CellNotFoundInFaithTrackException -> exception thrown if the provided cell doesn't exist in this
     * faith track.
     */
    Section findSectionOfThisCell(Cell currentCell) throws CellNotFoundInFaithTrackException {
        for(Section s : listOfSection) {
            try {
                s.searchInThisSection(currentCell);
                return s;
            } catch (CellNotFoundInSectionException e) {
                //do nothing and go to the next section in the arraylist.
            }
        }
        throw new CellNotFoundInFaithTrackException();
    }


    /**
     * Method that move the marker that belongs to the provided player by a provide number of cells.
     * @param player -> player whose marker to move.
     * @param numberOfSteps -> number of cells.
     * @throws Exception
     */
    void moveMarkerForward(Player player, int numberOfSteps) throws Exception {
        FaithMarker faithMarker = mapOfFaithMarker.get(player);
        for(int i = 0; i < numberOfSteps; i++) {
            Cell nextCell = nextCell(faithMarker.getCurrentCell());
            faithMarker.updateCurrentCell(nextCell);
            nextCell.activateCell(this, player);
            if(nextCell.equals(lastCellInFaithTrack())) {
                throw new GameOverByFaithTrackException();
            }
        }
    }


    /**
     * Getter method for the hashmap contained in this class.
     * @return -> the entire hashmap mapOfFaithMarker.
     */
    HashMap<Player, FaithMarker> getMapOfFaithMarker() {
        return mapOfFaithMarker;
    }


    /**
     * Method that returns the last cell of this faith track.
     * @return -> the last cell of this faith track.
     */
    private Cell lastCellInFaithTrack() throws WrongCellIndexException {
        return listOfSection.get(listOfSection.size() - 1).lastCellInSection();
    }
}
