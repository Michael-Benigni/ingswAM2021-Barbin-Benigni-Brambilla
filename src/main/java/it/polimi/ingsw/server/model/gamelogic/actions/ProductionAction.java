package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

import java.util.ArrayList;

interface ProductionAction extends MutualExclusiveAction {

    @Override
    default boolean isValid(Turn turn) {
        try {
            turn.startProductionPhase();
        } catch (NoValidActionException e) {
            try {
                turn.consumeTokenInProductionPhase();
            } catch (NoValidActionException e1) {
                return false;
            }
        }
        return true;
    }
}

