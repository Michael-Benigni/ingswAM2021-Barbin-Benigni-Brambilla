package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.Strongbox;

import java.util.ArrayList;

class StrogboxAction extends Action {
    private final String storeOrRemove;
    private final StorableResource resource;

    public StrogboxAction(String storeOrRemove, StorableResource resource) {
        super();
        this.storeOrRemove = storeOrRemove;
        this.resource = resource;
    }

    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        switch (this.storeOrRemove) {
            case "store" : {
                strongbox.store(this.resource);
                break;
            }
            case "remove" : {
                strongbox.remove(this.resource);
                break;
            }
            default:
        }
    }
}
