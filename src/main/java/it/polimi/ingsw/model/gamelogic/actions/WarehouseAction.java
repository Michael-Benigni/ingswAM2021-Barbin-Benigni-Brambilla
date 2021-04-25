package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;

class WarehouseAction extends Action {
    private final String storeOrRemove;
    private final StorableResource resource;
    private final int depotIdx;

    public WarehouseAction(String storeOrRemove, StorableResource resource, int depotIdx) {
        this.storeOrRemove = storeOrRemove;
        this.resource = resource;
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
                warehouse.store(resource, depotIdx);
                break;
            }
            case "remove": {
                warehouse.remove(resource, depotIdx);
            }
            default:
        }
    }
}
