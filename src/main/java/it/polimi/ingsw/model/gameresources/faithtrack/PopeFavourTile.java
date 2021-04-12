package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.exception.TileAlreadyActivatedException;
import it.polimi.ingsw.model.VictoryPoint;

/**
 * Class that represents the tile that can be flipped to increase the number of victory points
 */
public class PopeFavourTile {

    private final int numVictoryPoints;
    private boolean isAlreadyActivated;

    /**
     * Constructor method of this class.
     * @param numVictoryPoints -> integer useful to set the value of this tile.
     */
    public PopeFavourTile(int numVictoryPoints) {
        this.numVictoryPoints = numVictoryPoints;
        this.isAlreadyActivated = false;
    }


    /**
     * Method that returns a victory point if the tile is still active.
     * @return -> an instance of "VictoryPoint" class.
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     * @throws TileAlreadyActivatedException -> exception thrown if this tile is already activated.
     */
    VictoryPoint activateTile() throws NegativeVPAmountException, TileAlreadyActivatedException {
        if(!(this.isAlreadyActivated)) {
            VictoryPoint victoryPoint = new VictoryPoint(numVictoryPoints);
            this.isAlreadyActivated = true;
            return victoryPoint;
        }
        else {
            throw new TileAlreadyActivatedException();
        }
    }
}
