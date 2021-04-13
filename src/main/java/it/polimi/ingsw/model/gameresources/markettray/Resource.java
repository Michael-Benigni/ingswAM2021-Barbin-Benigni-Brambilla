package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.model.Player;

/**
 * Interface that join all the resources (storable, faith points...) in this unique common name
 */
public abstract class Resource implements Cloneable{

    /**
     * This method "activate()" will be redefined in every class that implements this interface
     */
    protected abstract void activate(Player player) throws Exception;

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
