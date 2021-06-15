package it.polimi.ingsw.server.model.gameresources.faithtrack;

import java.util.ArrayList;

/**
 * Class that represents the section that is not a vatican report one.
 */
public class ClassicSection extends Section{

    /**
     * Constructor method of this class.
     */
    public ClassicSection(ArrayList<Cell> listCell) {
        super(listCell);
    }

    @Override
    public String getInfo() {
        return " ";
    }


}
