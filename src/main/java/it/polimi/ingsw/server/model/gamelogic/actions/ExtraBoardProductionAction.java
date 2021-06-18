package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.AlreadyUsedForProductionException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;

public class ExtraBoardProductionAction implements ProductionAction {
    private final PayAction payActions;
    private final StorableResource resourceProduced;
    private final int numExtraPower;

    public ExtraBoardProductionAction(PayAction payActions, StorableResource resourceProduced, int numExtraPower) {
        this.payActions = payActions;
        this.resourceProduced = resourceProduced;
        this.numExtraPower = numExtraPower;
    }


    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        PersonalBoard.ExtraProductionPower power = player.getPersonalBoard().getExtraPower(numExtraPower);
        ArrayList<Producible> allProduced = power.produce(player, resourceProduced);
        if (power.isAvailableForProduction()) {
            payActions.setResource(power.getConsumedResource());
            UnboundedResourcesContainer cost = new UnboundedResourcesContainer();
            cost.store(power.getConsumedResource());
            payActions.payOrUndo(game, player, cost);
            if (!cost.getAllResources().isEmpty()) {
                game.getCurrentTurn().undo(game, player);
            }
            else {
                game.getCurrentTurn().clearCache();
                for (Producible producible : allProduced)
                    producible.onProduced(player, game);
                power.setAvailableForProduction(false);
            }
        } else
            throw new AlreadyUsedForProductionException();
    }
}
