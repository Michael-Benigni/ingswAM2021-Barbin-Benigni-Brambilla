package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.exception.NotUndoableAction;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.Strongbox;

class StrongboxAction extends PayAction {
    private final String storeOrRemove;

    public StrongboxAction(String storeOrRemove, StorableResource resource) {
        super(resource);
        this.storeOrRemove = storeOrRemove;
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
        if(storeOrRemove == "remove")
            return new StrongboxAction("store", getResource());
        return new StrongboxAction("remove", getResource());
    }
}
