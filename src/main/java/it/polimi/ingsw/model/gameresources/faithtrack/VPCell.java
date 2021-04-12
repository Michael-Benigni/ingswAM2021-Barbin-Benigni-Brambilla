package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;

/**
 * Class that represents a cell in the faith truck that can earn a player some victory points.
 */
public class VPCell extends Cell{

    private int numVictoryPoints;

    /**
     * Constructor method of this class. It reads from the database the value of "this.numVictoryPoints".
     */
    public VPCell(int numVictoryPoints) {
        this.numVictoryPoints = numVictoryPoints;
    }


    /**
     * Method inherited by "Cell" class. In this case it would create an instance of VictoryPoints...
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     */
    @Override
    protected void activateCell(FaithMarker faithMarker) throws NegativeVPAmountException {
        VictoryPoint currentVP = new VictoryPoint(numVictoryPoints);
        faithMarker.updateVictoryPointInFaithMarker(currentVP);
    }
}
