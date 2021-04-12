package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.model.Player;

/**
 * Interface that join all the resources (storable, faith points...) in this unique common name
 */
public abstract class Resource {

    /**
     * This method "activate()" will be redefined in every class that implements this interface
     */
    protected abstract void activate(Player player) throws Exception;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
