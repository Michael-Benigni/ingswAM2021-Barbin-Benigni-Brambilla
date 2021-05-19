package it.polimi.ingsw.server.model.gameresources.stores;


import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;

import java.util.ArrayList;

/**
 * Class that represents the player's strongbox on the personal board. It's a collection of resources
 */
public class Strongbox extends UnboundedResourcesContainer implements Cloneable {


    /**
     * @return the cloned object
     */
    @Override
    protected Object clone() {
        return new Strongbox();
    }
}
