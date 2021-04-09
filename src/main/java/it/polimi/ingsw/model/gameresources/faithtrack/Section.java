package it.polimi.ingsw.model.gameresources.faithtrack;

import java.util.ArrayList;

/**
 * Abstract class that represents a section of the faith track. A section can be a classic one or a vatican report one.
 */
public abstract class Section {

    ArrayList<Cell> listCell;

    //TODO: this constructor method is wrong. It's used only to build correctly the faith track on the json file.
    public Section(ArrayList<Cell> listCell) {
        this.listCell = listCell;
    }

}
