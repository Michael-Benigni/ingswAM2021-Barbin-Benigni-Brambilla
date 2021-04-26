package it.polimi.ingsw.model.gameresources;

import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gamelogic.actions.Player;

/**
 * Interface that join all the resources (storable, faith points...) in this unique common name
 */
public interface Resource extends Cloneable{

    /**
     * This method "use()" will be redefined in every class that implements this interface
     */
    void activate(Player player, Game game) throws Exception;

    Object clone() throws CloneNotSupportedException;
}
