package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.exception.NotUndoableAction;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;

class WarehouseAction extends PayAction {
    private final String storeOrRemove;
    private final int depotIdx;

    public WarehouseAction(String storeOrRemove, StorableResource resourceToPay, int depotIdx) {
        super(resourceToPay);
        this.storeOrRemove = storeOrRemove;
        this.depotIdx = depotIdx;
    }

    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
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
