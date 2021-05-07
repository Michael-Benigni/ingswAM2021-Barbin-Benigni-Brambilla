package it.polimi.ingsw.server.model.gameresources.stores;


/**
 * Class that represents the player's strongbox on the personal board. It's a collection of resources
 */
public class Strongbox extends UnboundedResourcesContainer implements Cloneable{

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Strongbox();
    }
}
