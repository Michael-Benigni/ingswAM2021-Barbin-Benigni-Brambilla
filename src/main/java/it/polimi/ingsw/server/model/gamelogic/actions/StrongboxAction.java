package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.Strongbox;

import java.util.Objects;

public class StrongboxAction extends PayAction {

    public StrongboxAction(StoreOrRemove storeOrRemove, StorableResource resource) {
        super(resource, storeOrRemove);
    }


    @Override
    public void perform(Game game, Player player) throws NegativeResourceAmountException, NotContainedResourceException {
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        switch (getStoreOrRemove ()) {
            case STORE: {
                strongbox.store(this.getResource());
                break;
            }
            case REMOVE: {
                strongbox.remove(this.getResource());
                break;
            }
            default:
        }
    }

    @Override
    public PayAction getUndoAction() {
        if(getStoreOrRemove ().equals(StoreOrRemove.REMOVE))
            return new StrongboxAction(StoreOrRemove.STORE, getResource());
        return new StrongboxAction(StoreOrRemove.REMOVE, getResource());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StrongboxAction)) return false;
        StrongboxAction that = (StrongboxAction) o;
        return Objects.equals(getStoreOrRemove (), that.getStoreOrRemove ());
    }
}
