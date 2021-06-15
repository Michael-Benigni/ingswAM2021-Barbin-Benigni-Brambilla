package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;

public class EndTurnAction implements FirstTurnAction {
    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        game.performEndTurnAction ();
    }

    @Override
    public boolean isValid(Turn turn) {
        return true;
    }
}
