package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.exception.ResourceOverflowInDepotException;
import it.polimi.ingsw.server.exception.WrongDepotIndexException;
import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;

import java.util.Objects;

class WarehouseAction extends PayAction implements FirstTurnAction {
    private final String storeOrRemove;
    private final int depotIdx;

    public WarehouseAction(String storeOrRemove, StorableResource resourceToPay, int depotIdx) {
        super(resourceToPay);
        this.storeOrRemove = storeOrRemove;
        this.depotIdx = depotIdx;
    }


    @Override
    public void perform(Game game, Player player) throws WrongDepotIndexException, NegativeResourceAmountException,
            NotEqualResourceTypeException, ResourceOverflowInDepotException {
        WarehouseDepots warehouse = player.getPersonalBoard().getWarehouseDepots();
        switch (storeOrRemove) {
            case "store": {
                try {
                    warehouse.store(getResource(), depotIdx);
                } catch (ResourceOverflowInDepotException e) {
                    player.getPersonalBoard().getTempContainer().store(e.getResource());
                    throw new ResourceOverflowInDepotException(e.getResource());
                }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseAction)) return false;
        WarehouseAction that = (WarehouseAction) o;
        return depotIdx == that.depotIdx && Objects.equals(storeOrRemove, that.storeOrRemove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeOrRemove, depotIdx);
    }
}
