package it.polimi.ingsw.model;


/**
 * Interface that join all the resources (stackable, faith points...) in this unique common name
 */
public interface Resource {

    /**
     * This method "activate()" will be redefined in every class that implements this interface
     */
    void activate();
}
