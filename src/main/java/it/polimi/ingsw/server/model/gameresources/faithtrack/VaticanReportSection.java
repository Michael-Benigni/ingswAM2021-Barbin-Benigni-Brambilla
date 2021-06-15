package it.polimi.ingsw.server.model.gameresources.faithtrack;

import java.util.ArrayList;

/**
 * Class that represents a section of the faith truck that is linked to a favour tile.
 */
public class VaticanReportSection extends Section{

    /**
     * Constructor method of this class.
     * @param listCell -> arraylist of cells.
     */
    public VaticanReportSection(ArrayList<Cell> listCell) {
        super(listCell);
    }

    @Override
    public String getInfo() {
        return "Vatican";
    }

}
