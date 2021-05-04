package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.Producible;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;

class ExtraBoardProductionAction extends Action {
    private final PayAction fromWhere;
    private final StorableResource resourceProduced;
    private final int numExtraPower;

    public ExtraBoardProductionAction(PayAction fromWhere, StorableResource resourceProduced, int numExtraPower) {
        this.fromWhere = fromWhere;
        this.resourceProduced = resourceProduced;
        this.numExtraPower = numExtraPower;
    }

    /**
     * @return the type of the Action
     */
    @Override
    protected ActionType getType() {
        return null;
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
        }
    }
}
