package it.polimi.ingsw.model;

/**
 * This class represents the resource contained into a white marble
 */
public class EmptyResource implements Resource{

    /**
     * Constructor method of EmptyResource class
     */
    public EmptyResource() {
    }


    /**
     * Method inherited by the implementation of "Resource" interface, but doesn't append any function
     */
    @Override
    public void activate() {
        //Empty method
    }
}
