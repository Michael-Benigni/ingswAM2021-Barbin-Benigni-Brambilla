package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.AlreadyUsedForProductionException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class BoardProductionAction implements ProductionAction {
    private final StorableResource produced;
    private final ArrayList<PayAction> payActions;

    BoardProductionAction(StorableResource produced, ArrayList<PayAction> payActions) {
        this.produced = produced;
        this.payActions = payActions;
    }


    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        List<StorableResource> resourcesToPay = payActions.stream ().map (PayAction::getResource).collect (Collectors.toList ());
        int numOfResources = resourcesToPay.stream ().map (StorableResource::getAmount).reduce(0, (a, b) -> a + b);
        if (player.getPersonalBoard().isAvailableForProduction() && numOfResources >= player.getPersonalBoard ().getNumOfResourcesForProduction()) {
            UnboundedResourcesContainer cost = new UnboundedResourcesContainer();
            for (PayAction action : payActions)
                cost.store(action.getResource());
            for (PayAction action : payActions)
                action.payOrUndo(game, player, cost);
            if (!cost.getAllResources().isEmpty())
                game.getCurrentTurn().undo(game, player);
            else {
                game.getCurrentTurn().clearCache();
                produced.store(player);
                player.getPersonalBoard().setAvailableForProduction(false);
            }
        }
        else
            throw new AlreadyUsedForProductionException();
    }
}
