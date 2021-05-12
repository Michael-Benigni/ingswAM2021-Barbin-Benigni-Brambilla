package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

import java.util.ArrayList;

public interface ProductionAction extends MutualExclusiveAction {

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

class EndProductionAction implements Action {

    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        ArrayList<StorableResource> resources = player.getPersonalBoard().getTempContainer().getAllResources();
        player.getPersonalBoard().getStrongbox().storeAll(resources);
        for (Producer producer : player.getPersonalBoard().getAllProducers())
            producer.setAvailableForProduction(true);
        game.getCurrentTurn().endProductionPhase();
    }
}
