package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.AlreadyUsedForProuctionException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;

class ExtraBoardProductionAction implements ProductionAction {
    private final PayAction fromWhere;
    private final StorableResource resourceProduced;
    private final int numExtraPower;

    ExtraBoardProductionAction(PayAction fromWhere, StorableResource resourceProduced, int numExtraPower) {
        this.fromWhere = fromWhere;
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
        if (power.isAvailableForProduction()) {
            fromWhere.setResource(power.getConsumedResource());
            UnboundedResourcesContainer cost = new UnboundedResourcesContainer();
            cost.store(power.getConsumedResource());
            fromWhere.payOrUndo(game, player, cost);
            if (!cost.getAllResources().isEmpty())
                game.getCurrentTurn().undo(game, player);
            else {
                game.getCurrentTurn().clearCache();
                ArrayList<Producible> allProduced = player.getPersonalBoard().getExtraPower(numExtraPower).produce(player, resourceProduced);
                for (Producible producible : allProduced)
                    producible.onProduced(player, game);
                power.setAvailableForProduction(false);
            }
        } else
            throw new AlreadyUsedForProuctionException();
    }
}
