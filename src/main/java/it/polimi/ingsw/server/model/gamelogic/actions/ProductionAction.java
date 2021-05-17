package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.Turn;

interface ProductionAction extends Action {

    @Override
    default boolean isValid(Turn turn) {
        try {
            turn.consumeTokenInProductionPhase();
        } catch (NoValidActionException e1) {
            return false;
        }
        return true;
    }
}

