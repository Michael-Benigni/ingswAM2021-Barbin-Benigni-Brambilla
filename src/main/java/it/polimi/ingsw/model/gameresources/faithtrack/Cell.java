package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.model.Player;

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
    protected abstract void activateCell(FaithTrack faithTrack, Player player) throws Exception;


}
