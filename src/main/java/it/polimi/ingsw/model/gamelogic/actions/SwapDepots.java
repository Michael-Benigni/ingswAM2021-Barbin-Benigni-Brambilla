package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;

import java.util.ArrayList;

class SwapDepots extends Action {
    private final int depot1;
    private final int depot2;

    SwapDepots(int depot1, int depot2) {
        super();
        this.depot1 = depot1;
        this.depot2 = depot2;
    }

    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        StorableResource overflow = player.getPersonalBoard().getWarehouseDepots().swapDepots(depot1, depot2);
        player.getPersonalBoard().getTempContainer().store(overflow);
    }
}
