package it.polimi.ingsw.model.gameresources.markettray;


import it.polimi.ingsw.exception.NegativeResourceAmountException;

/**
 * Interface that join all the resources (storable, faith points...) in this unique common name
 */
public abstract class Resource {

    /**
     * This method "activate()" will be redefined in every class that implements this interface
     */
    protected void activate() {

    }

    /**
     * this method create a copy of the resource
     * @return the created copy
     * @throws NegativeResourceAmountException
     */
    protected Resource copyResource() throws CloneNotSupportedException {
        return (Resource) this.clone();
    }
}
