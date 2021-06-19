package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.AlreadyUsedForProductionException;
import it.polimi.ingsw.server.model.exception.InvalidAmountForExtraProductionProducedResource;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;

public class ExtraBoardProductionAction implements ProductionAction {
    private final PayAction payAction;
    private final StorableResource resourceProduced;
    private final int numExtraPower;

    public ExtraBoardProductionAction(PayAction payAction, StorableResource resourceProduced, int numExtraPower) {
        this.payAction = payAction;
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
        if (power.isAvailableForProduction() && payAction.getResource ().getAmount () == power.getAmountToPay () && resourceProduced.getAmount () == power.getAmountToProduce()) {
            payAction.setResource(power.getConsumedResource());
            UnboundedResourcesContainer cost = new UnboundedResourcesContainer();
            cost.store(power.getConsumedResource());
            payAction.payOrUndo(game, player, cost);
            if (!cost.getAllResources().isEmpty()) {
                game.getCurrentTurn().undo(game, player);
            }
            else {
                game.getCurrentTurn().clearCache();
                for (Producible producible : allProduced)
                    producible.onProduced(player, game);
                power.setAvailableForProduction(false);
            }
        } else {
            if (payAction.getResource ().getAmount () != power.getAmountToPay ())
                throw new InvalidAmountForExtraProductionProducedResource (payAction.getResource (), power.getAmountToPay ());
            else if (resourceProduced.getAmount () != power.getAmountToProduce ())
                throw new InvalidAmountForExtraProductionProducedResource (resourceProduced, power.getAmountToProduce ());
            throw new AlreadyUsedForProductionException ();
        }
    }
}
