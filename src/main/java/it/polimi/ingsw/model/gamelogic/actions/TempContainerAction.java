package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.Resource;
import it.polimi.ingsw.model.gameresources.Storable;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.TemporaryContainer;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;

class TempContainerAction extends Action {
    private final String storeOrRemove;
    private final StorableResource resource;
    private final int depotIdx;

    public TempContainerAction(String storeOrRemove, StorableResource resource, int depotIdx) {
        super();
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
        TemporaryContainer tempCont = player.getPersonalBoard().getTempContainer();
        WarehouseDepots warehouse = player.getPersonalBoard().getWarehouseDepots();
        switch (storeOrRemove) {
            case "remove" : {
                tempCont.remove(this.resource);
                new WarehouseAction("store", resource, depotIdx).perform(game, player);
                break;
            }
            case "store": {
                tempCont.store(this.resource);
                new WarehouseAction("remove", resource, depotIdx).perform(game, player);
                break;
            }
            case "clear": {
                tempCont.clear();
            }
            default:
        }
    }
}
