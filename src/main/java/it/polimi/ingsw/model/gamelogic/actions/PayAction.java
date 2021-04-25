package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import java.util.Optional;

class PayAction extends Action {
    private final String fromWhere;
    private final StorableResource resource;
    private final Optional<Integer> depotIdx;

    public PayAction(String fromWhere, StorableResource resource, int depotIdx) {
        super();
        this.fromWhere = fromWhere;
        this.resource = resource;
        this.depotIdx = Optional.of(depotIdx);
    }

    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        PersonalBoard board = player.getPersonalBoard();
        switch (this.fromWhere) {
            case "strongbox": {
                board.getStrongbox().remove(this.resource);
            }
            case "warehouse": {
                board.getWarehouseDepots().remove(this.resource, this.depotIdx.get());
            }
        }
    }

    Action undoAction () {
        switch (this.fromWhere) {
            case "warehouse": {
                return new WarehouseAction("store", this.resource, this.depotIdx.get());
            }
            case "strongbox": {
                return new StrogboxAction("store", this.resource);
            }
            default: return null;
        }
    }

    StorableResource getResource() {
        StorableResource clone = null;
        try {
            clone = (StorableResource) this.resource.clone();
        } catch (CloneNotSupportedException e) {
        }
        return clone;
    }
}
