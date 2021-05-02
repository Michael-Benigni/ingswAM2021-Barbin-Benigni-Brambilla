package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.CellNotFoundInSectionException;
import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.model.gamelogic.actions.VictoryPoint;

import java.util.Objects;

/**
 * Class that represents the indicator of a player's position on the faith track.
 */
public class FaithMarker {

    private Cell currentCell;
    private VictoryPoint lastVictoryPoint;

    /**
     * Constructor method of this class.
     */
    public FaithMarker(Cell startingCell) {
        lastVictoryPoint = new VictoryPoint(0);
        this.currentCell = startingCell;
    }


    /**
     * Method that update the victory points gained by the last VPCell reached by the faith marker. Then also increase
     * the amount of victory points of that player.
     * @param newVictoryPoint -> number of earned victory points.
     * @throws NegativeVPAmountException -> can be thrown by "decreaseVictoryPoint" method of "VictoryPoint" class.
     */
    VictoryPoint updateLastVictoryPoint(VictoryPoint newVictoryPoint) throws NegativeVPAmountException {
        VictoryPoint temporaryVictoryPoint = newVictoryPoint;
        temporaryVictoryPoint.decreaseVictoryPoints(lastVictoryPoint);
        lastVictoryPoint = newVictoryPoint;
        return temporaryVictoryPoint;
    }


    /**
     * Method that returns if the current cell is contained in the provided section.
     * @param currentSection -> section to be scouted.
     * @return -> boolean: true if the current cell is in the provided section.
     */
    boolean ifIsInThisSection(Section currentSection) {
        try {
            currentSection.searchInThisSection(this.currentCell);
            return true;
        } catch(CellNotFoundInSectionException e){
            return false;
        }
    }


    /**
     * Getter method of "currentCell" method of this class.
     * @return -> the current cell of this faith marker.
     */
    Cell getCurrentCell() {
        return currentCell;
    }


    /**
     * Method that update the current cell of this marker.
     * @param newCell -> cell to be substituted to this current cell.
     */
    void updateCurrentCell(Cell newCell) {
        this.currentCell = newCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaithMarker that = (FaithMarker) o;
        return Objects.equals(currentCell, that.currentCell) && Objects.equals(lastVictoryPoint, that.lastVictoryPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentCell, lastVictoryPoint);
    }
}
