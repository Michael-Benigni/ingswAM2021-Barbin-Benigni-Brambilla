package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NoEmptyResourceException;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gamelogic.Game;

/**
 * interface that represents
 * the effects of the leader card
 */
public interface Effect {
    void applyOn(Player player, Game game) throws NegativeResourceAmountException, NoEmptyResourceException;
}