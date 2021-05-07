package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Player;

/**
 * interface that represents the
 * requirements of the leader cards
 */
public interface Requirement {
    boolean containedIn(Player player) throws NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, EmptySlotException, WrongSlotDevelopmentIndexException;
}

