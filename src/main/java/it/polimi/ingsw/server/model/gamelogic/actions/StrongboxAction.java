package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.Strongbox;

import java.util.Objects;

class StrongboxAction extends PayAction implements FirstTurnAction{
    private final String storeOrRemove;

    StrongboxAction(String storeOrRemove, StorableResource resource) {
        super(resource);
        this.storeOrRemove = storeOrRemove;
    }


    @Override
    public void perform(Game game, Player player) throws NegativeResourceAmountException, NotContainedResourceException {
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        switch (this.storeOrRemove) {
            case "store" : {
                strongbox.store(this.getResource());
                break;
            }
            case "remove" : {
                strongbox.remove(this.getResource());
                break;
            }
            default:
        }
    }

    @Override
    public Action getUndoAction() {
        if(storeOrRemove.equals("remove"))
            return new StrongboxAction("store", getResource());
        return new StrongboxAction("remove", getResource());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StrongboxAction)) return false;
        StrongboxAction that = (StrongboxAction) o;
        return Objects.equals(storeOrRemove, that.storeOrRemove);
    }
}
