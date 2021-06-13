package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.TemporaryContainer;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;

class TempContainerAction implements FirstTurnAction {
    private final String storeOrRemove;
    private final StorableResource resource;
    private final int depotIdx;

    TempContainerAction(String storeOrRemove, StorableResource resource, int depotIdx) {
        super();
        this.storeOrRemove = storeOrRemove;
        this.resource = resource;
        this.depotIdx = depotIdx;
    }


    @Override
    public void perform(Game game, Player player) throws WrongDepotIndexException, NegativeResourceAmountException,
            EmptyDepotException, SameResourceTypeInDifferentDepotsException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException, NotContainedResourceException {
        TemporaryContainer tempCont = player.getPersonalBoard().getTempContainer();
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
            default:
        }
    }

    @Override
    public boolean isValid(Turn turn) {
        return true;
    }
}
