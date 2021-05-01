package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.UnboundedResourcesContainer;

import java.util.ArrayList;
import java.util.Optional;

public abstract class PayAction extends Action {

    public abstract Action undoAction();

    void payOrUndo (Game game, Player player) throws Exception {
        try {
            perform(game, player);
            game.getCurrentTurn().addUndoableAction(this);
        } catch (Exception e) {
            undoAction().perform(game, player);
            for (PayAction payAction : game.getCurrentTurn().getUndoableActions()) {
                payAction.undoAction().perform(game, player);
            }
        }
    }

}
