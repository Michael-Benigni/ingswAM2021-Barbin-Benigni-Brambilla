package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.exception.TileAlreadyActivatedException;
import it.polimi.ingsw.model.VictoryPoint;

/**
 * Class that represents the tile that can be flipped to increase the number of victory points
 */
public class PopeFavourTile {

    private final VictoryPoint victoryPoint;
    private boolean isAlreadyActivated;

    /**
     * Constructor method of this class.
     * @param victoryPoints -> victory points earned with this tile.
     */
    public PopeFavourTile(VictoryPoint victoryPoints) {
        this.victoryPoint = victoryPoints;
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
            this.isAlreadyActivated = true;
            return this.victoryPoint;
        }
        else {
            throw new TileAlreadyActivatedException();
        }
    }
}
