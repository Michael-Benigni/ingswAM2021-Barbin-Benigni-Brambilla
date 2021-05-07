package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

class EndTurnAction implements Action {

    @Override
    public void perform(Game game, Player player) throws Exception {
        new DiscardAllResources().perform(game, player);
        game.getCurrentTurn().terminate(game);
        game.setNextPlayer();
    }
}
