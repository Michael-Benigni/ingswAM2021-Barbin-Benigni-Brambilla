package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;

/**
 * Class that represents a Pope Space cell on the faith truck.
 */
public class PopeSpace extends Cell{

    /**
     * Constructor method of this class.
     */
    public PopeSpace() {
    }

    /**
     * Method inherited from "Cell" class. In this case this method would activate the right Pope Favour Tile and
     * it would call a method on the other player that checks if they are on a Vatican Report section.
     * @param faithMarker
     */
    @Override
    protected void activateCell(FaithMarker faithMarker) throws Exception {
        Player player = faithMarker.getPlayer();
        GameBoard gameBoard = player.getGameBoard();
        FaithTrack faithTrack = gameBoard.getFaithTrack();
        faithTrack.activatePopeSpaceEffect(this, player);
    }


    /**
     * Method that return if two objects are both instances of this class.
     * @param o -> object to be compared.
     * @return -> boolean: true if both are instances of "PopeSpace" class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
