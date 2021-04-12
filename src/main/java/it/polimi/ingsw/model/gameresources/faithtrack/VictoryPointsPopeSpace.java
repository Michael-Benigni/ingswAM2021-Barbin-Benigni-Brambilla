package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.model.VictoryPoint;

/**
 * Class that represents a particular type of pope space that gives some victory points.
 */
public class VictoryPointsPopeSpace extends PopeSpace{

    int numVictoryPoints;

    /**
     * Constructor method of this class.
     * @param numVictoryPoints -> number of victory points.
     */
    public VictoryPointsPopeSpace(int numVictoryPoints) {
        super();
        this.numVictoryPoints = numVictoryPoints;
    }

    /**
     * Method inherited by "Cell" class. In this case this method does the same actions of "VPCell" and "PopeSpace".
     * @param faithMarker
     * @throws Exception
     */
    @Override
    public void activateCell(FaithMarker faithMarker) throws Exception {
        VictoryPoint currentVP = new VictoryPoint(numVictoryPoints);
        faithMarker.updateVictoryPointInFaithMarker(currentVP);
        super.activateCell(faithMarker);
    }
}
