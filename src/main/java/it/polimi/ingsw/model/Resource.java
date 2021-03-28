package it.polimi.ingsw.model;


import it.polimi.ingsw.exception.NegativeResourceAmountException;

/**
 * Interface that join all the resources (storable, faith points...) in this unique common name
 */
public interface Resource {

    /**
     * This method "activate()" will be redefined in every class that implements this interface
     */
    void activate();

    /**
     * this method create a copy of the resource
     * @return the created copy
     * @throws NegativeResourceAmountException
     */
    Resource copyResource() throws NegativeResourceAmountException;
}
