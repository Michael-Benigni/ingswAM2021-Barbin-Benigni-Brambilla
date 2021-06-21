package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.FirstTurn;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import java.util.Objects;

public class WarehouseAction extends PayAction implements FirstTurnAction {
    private final int depotIdx;

    public WarehouseAction(StoreOrRemove storeOrRemove, StorableResource resourceToPay, int depotIdx) {
        super(resourceToPay, storeOrRemove);
        this.depotIdx = depotIdx;
    }

    @Override
    public void perform(Game game, Player player) throws WrongDepotIndexException, NegativeResourceAmountException,
            NotEqualResourceTypeException, ResourceOverflowInDepotException, EmptyDepotException, SameResourceTypeInDifferentDepotsException {
        WarehouseDepots warehouse = player.getPersonalBoard().getWarehouseDepots();
        switch (getStoreOrRemove ()) {
            case STORE: {
                warehouse.store(getResource(), depotIdx);
                break;
            }
            case REMOVE: {
                warehouse.remove(getResource(), depotIdx);
            }
            default:
        }
    }

    @Override
    public PayAction getUndoAction() {
        if (getStoreOrRemove ().equals (StoreOrRemove.REMOVE))
            return new WarehouseAction(StoreOrRemove.STORE, getResource(), depotIdx);
        return new WarehouseAction(StoreOrRemove.REMOVE, getResource(), depotIdx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseAction)) return false;
        WarehouseAction that = (WarehouseAction) o;
        return depotIdx == that.depotIdx && Objects.equals(getStoreOrRemove (), that.getStoreOrRemove ());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreOrRemove (), depotIdx);
    }

    /**
     * @param turn turn in which is checked the validity of this action
     * @return true if this Action can be performed after the Action performedActions, otherwise it returns false. This check
     * is done looking at the type of the performedActions, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    @Override
    public boolean isValid(FirstTurn turn) {
        return true;
    }

    @Override
    public boolean isValid(Turn turn) {
        return false;
    }
}
