package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.gameresources.Resource;

/**
 * This class represents the resource contained into a white marble
 */
class EmptyResource implements Resource {

    /**
     * Constructor method of EmptyResource class
     */
    EmptyResource() {
    }


    /**
     * Method inherited by the implementation of "Resource" interface, but doesn't append any function
     */
    @Override
    public void activate() {
        //Empty method
    }

    /**
     * this method creates a copy of the object EmptyResource
     * @return the created copy
     * @throws NegativeResourceAmountException
     */
    @Override
    public Resource copyResource() throws NegativeResourceAmountException {
        EmptyResource copy = new EmptyResource();
        return copy;
    }
}
