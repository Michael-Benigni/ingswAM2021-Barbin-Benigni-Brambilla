package it.polimi.ingsw.model.gameresources.faithtrack;

import java.util.ArrayList;

/**
 * Class that represents a section of the faith truck that is linked to a favour tile.
 */
public class VaticanReportSection extends Section{

    PopeFavourTile tile;

    /**
     * Constructor method of this class.
     * @param listCell -> arraylist of cells.
     * @param tile -> pope favour tile connected to this section.
     */
    public VaticanReportSection(ArrayList<Cell> listCell, PopeFavourTile tile) {
        super(listCell);
        this.tile = tile;
    }


    /**
     * Getter method for the "tile" attribute of this class.
     * @return -> the pope favour tile of this section.
     */
    PopeFavourTile getTile(){
        return this.tile;
    }
}
