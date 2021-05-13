package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NoEmptyResourceException;
import it.polimi.ingsw.server.model.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.model.exception.ResourceOverflowInDepotException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Game;

/**
 * interface that represents
 * the effects of the leader card
 */
public interface Effect {
    void applyOn(Player player, Game game) throws NegativeResourceAmountException, NoEmptyResourceException, NotEqualResourceTypeException, ResourceOverflowInDepotException;
}