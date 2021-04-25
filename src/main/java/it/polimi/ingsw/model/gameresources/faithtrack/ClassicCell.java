package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.model.gamelogic.actions.Player;

/**
 * Class that represents an empty cell in the faith truck.
 */
public class ClassicCell extends Cell{

    /**
     * Constructor method of this class.
     */
    public ClassicCell() {
    }

    /**
     * Method inherited by "Cell" class. In this case, it doesn't append anything.
     * @param faithTrack
     * @param player
     */
    @Override
    protected void activateCell(FaithTrack faithTrack, Player player) {
    }


    /**
     * Method that return if two objects are both instances of this class.
     * @param o -> object to be compared.
     * @return -> boolean: true if both are instances of "ClassicCell" class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
