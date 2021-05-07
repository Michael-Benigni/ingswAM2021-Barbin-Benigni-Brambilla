package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;

class WarehouseAction extends PayAction implements FirstTurnAction {
    private final String storeOrRemove;
    private final int depotIdx;

    public WarehouseAction(String storeOrRemove, StorableResource resourceToPay, int depotIdx) {
        super(resourceToPay);
        this.storeOrRemove = storeOrRemove;
        this.depotIdx = depotIdx;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        WarehouseDepots warehouse = player.getPersonalBoard().getWarehouseDepots();
        switch (storeOrRemove) {
            //TODO: is needed the store-case?
            case "store": {
                warehouse.store(getResource(), depotIdx);
                break;
            }
            case "remove": {
                warehouse.remove(getResource(), depotIdx);
            }
            default:
        }
    }

    @Override
    public Action getUndoAction() {
        if (this.storeOrRemove == "remove")
            return new WarehouseAction("store", getResource(), depotIdx);
        return new WarehouseAction("remove", getResource(), depotIdx);
    }
}
