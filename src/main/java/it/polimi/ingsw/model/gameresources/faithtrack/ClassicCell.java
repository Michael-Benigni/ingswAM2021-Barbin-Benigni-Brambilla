package it.polimi.ingsw.model.gameresources.faithtrack;

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
     * @param faithMarker
     */
    @Override
    protected void activateCell(FaithMarker faithMarker) {
    }
}
