package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Storable;

/**
 * This class represents the resource contained into a white marble
 */
public class EmptyResource implements Storable {

    /**
     * Constructor method of EmptyResource class
     */
    public EmptyResource() { }


    /**
     * Method inherited by the implementation of "Resource" interface, but doesn't append any function
     */
    @Override
    public void activate(Player player, Game game) {
        this.store(player);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public void store(Player player) {
        player.getPersonalBoard().getTempContainer().store(this);
    }
}
