package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

class SwapDepots implements FirstTurnAction {
    private final int depot1;
    private final int depot2;

    SwapDepots(int depot1, int depot2) {
        super();
        this.depot1 = depot1;
        this.depot2 = depot2;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        StorableResource overflow = player.getPersonalBoard().getWarehouseDepots().swapDepots(depot1, depot2);
        if(overflow != null){
            player.getPersonalBoard().getTempContainer().store(overflow);
        }
    }

    @Override
    public boolean isValid(Turn turn) {
        return true;
    }
}
