package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Player;

/**
 * interface that represents the
 * requirements of the leader cards
 */
public interface Requirement {
    boolean containedIn(Player player) throws CloneNotSupportedException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, EmptySlotException, WrongSlotDevelopmentIndexException;
}