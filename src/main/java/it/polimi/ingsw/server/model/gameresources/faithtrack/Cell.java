package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.gamelogic.Player;

/**
 * Abstract class that represents a cell of the faith track.
 */
public abstract class Cell {

    /**
     * Method that activates this cell. It will be inherited by all classes that extend this class.
     * @param faithTrack
     * @param player
     * @throws Exception
     */
    protected abstract void activateCell(FaithTrack faithTrack, Player player) throws CellNotFoundInFaithTrackException, NegativeVPAmountException;


}
