package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.Player;

/**
 * This class represents the resource contained into a white marble
 */
class EmptyResource extends Resource {

    /**
     * Constructor method of EmptyResource class
     */
    EmptyResource() {
    }


    /**
     * Method inherited by the implementation of "Resource" interface, but doesn't append any function
     */
    @Override
    protected void activate(Player player) {
        //Empty method
    }

    /**
     * this method creates a copy of the object EmptyResource
     * @return the created copy
     *
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
