package it.polimi.ingsw.server.model.gameresources.faithtrack;

import com.google.gson.JsonArray;
import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents the common faith track on the game board. Every player has a marker that indicates its position on the track.
 */
public class FaithTrack implements GameComponent {

    private ArrayList<Section> listOfSections;
    private HashMap<Player, FaithMarker> mapOfFaithMarkers;
    private ArrayList<Observer> observers;

    /**
     * Constructor method of this class. It builds the entire structure of the track.
     * @param arrayOfSections
     */
    public FaithTrack(ArrayList<Section> arrayOfSections) {
        this.listOfSections = new ArrayList<>(0);
        this.listOfSections = arrayOfSections;
        this.observers = new ArrayList<> ();
    }

    /**
     * Method that initializes the map of faithMarkers given the all players of the game
     * @param players
     */
    public void initMarkers(ArrayList<Player> players) {
        this.mapOfFaithMarkers = new HashMap<>(0);
        Cell firstCell = null;
        try {
            firstCell = firstCellInFaithTrack();
        } catch (WrongCellIndexException e) {
            e.printStackTrace();
        }
        for(Player p : players) {
            FaithMarker temporaryFaithMarker = new FaithMarker(firstCell);
            this.mapOfFaithMarkers.put(p, temporaryFaithMarker);
        }
    }

    /**
     * Method that returns the first cell of the track to the caller.
     *
     * @return -> the first cell of the track.
     */
    private Cell firstCellInFaithTrack() throws WrongCellIndexException {
        return listOfSections.get(0).getCell(0);
    }

    /**
     * Method that update the provided cell with the next on the track.
     * @param currentCell -> the current cell, so the caller wants the next one.
     * @return -> the next cell.
     */
    private Cell nextCell(Cell currentCell) throws WrongCellIndexException, CellNotFoundInFaithTrackException {
        for (Section s : listOfSections) {
            try {
                Cell nextCell = s.searchNextCellInSection(currentCell);
                return nextCell;
            } catch (LastCellInSectionException e) {
                int sectionIndex = listOfSections.indexOf(s) + 1;
                return listOfSections.get(sectionIndex).firstCellInSection();
            } catch (CellNotFoundInSectionException e) {
                //do nothing and go to the next section.
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
        for(Section s : listOfSections) {
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
    void moveMarkerForward(Player player, int numberOfSteps) throws WrongCellIndexException, CellNotFoundInFaithTrackException, NegativeVPAmountException, GameOverByFaithTrackException {
        FaithMarker faithMarker = this.getMapOfFaithMarkers().get(player);
        for(int i = 0; i < numberOfSteps; i++) {
            Cell nextCell = nextCell(faithMarker.getCurrentCell());
            faithMarker.updateCurrentCell(nextCell);
            nextCell.activateCell(this, player);
            if(nextCell == lastCellInFaithTrack()) {
                throw new GameOverByFaithTrackException();
            }
        }
    }

    /**
     * Getter method for the hashmap contained in this class.
     * @return -> the entire hashmap mapOfFaithMarker.
     */
    HashMap<Player, FaithMarker> getMapOfFaithMarkers() {
        return mapOfFaithMarkers;
    }

    /**
     * Method that returns the last cell of this faith track.
     * @return -> the last cell of this faith track.
     */
    private Cell lastCellInFaithTrack() {
        return listOfSections.get(listOfSections.size() - 1).lastCellInSection();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaithTrack)) return false;
        FaithTrack that = (FaithTrack) o;
        return listOfSections.equals(that.listOfSections) && mapOfFaithMarkers.equals(that.mapOfFaithMarkers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfSections, mapOfFaithMarkers);
    }

    public Set<Player> getPlayersFromFaithTrack() {
        return this.mapOfFaithMarkers.keySet();
    }

    @Override
    public Iterable<Observer> getObservers() {
        return this.observers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);
    }

    public void notifyInitialUpdate() {

    }
}