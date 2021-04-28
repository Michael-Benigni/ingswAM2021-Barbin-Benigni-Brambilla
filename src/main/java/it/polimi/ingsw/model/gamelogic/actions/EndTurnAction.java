package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;

class EndTurnAction extends Action {

    @Override
    protected ActionType getType() {
        return ActionType.END;
    }


    //TODO: clear temporaryCont and be sure to not have inconsistent states
    @Override
    public void perform(Game game, Player player) {
        game.getCurrentTurn().terminate(game);
    }
}
