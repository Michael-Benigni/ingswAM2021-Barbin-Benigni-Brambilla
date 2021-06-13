package it.polimi.ingsw.server.model.gamelogic.actions;


import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;

public class EndTurnAction implements FirstTurnAction {

    @Override
    public void perform(Game game, Player player) throws WrongInitialConfiguration, WrongCellIndexException,
            CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException {
        new DiscardAllResources().perform(game, player);
        game.getCurrentTurn().terminate(game);
        game.setNextPlayer();
    }

    @Override
    public boolean isValid(Turn turn) {
        return true;
    }
}
