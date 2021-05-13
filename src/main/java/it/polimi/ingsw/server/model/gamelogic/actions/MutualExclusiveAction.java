package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Turn;

interface MutualExclusiveAction extends Action {
    @Override
    default boolean isValid(Turn turn) {
        try {
            turn.consumeToken();
        } catch (NoValidActionException e) {
            return false;
        }
        return true;
    }
}
