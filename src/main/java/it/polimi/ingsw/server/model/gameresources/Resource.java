package it.polimi.ingsw.server.model.gameresources;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

/**
 * Interface that join all the resources (storable, faith points...) in this unique common name
 */
public interface Resource extends Cloneable {


    /**
     * This method "use()" will be redefined in every class that implements this interface
     */
    void activate(Player player, Game game) throws Exception;


    /**
     * @return This method clone returns another instance of the object on which is invoked
     */
    Object clone();
}
