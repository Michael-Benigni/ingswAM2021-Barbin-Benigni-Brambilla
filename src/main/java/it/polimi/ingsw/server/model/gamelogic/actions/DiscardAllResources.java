package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;

public class DiscardAllResources implements Action {

    @Override
    public void perform(Game game, Player player) throws WrongCellIndexException, CellNotFoundInFaithTrackException,
            GameOverByFaithTrackException, NegativeVPAmountException {
        FaithPoint points = player.getPersonalBoard().getTempContainer().getPenalty();
        for(Player p : game.getGameBoard().getFaithTrack().getPlayersFromFaithTrack()) {
            if (!(p == player))
                points.activate(p, game);
        }
        player.getPersonalBoard().getTempContainer().clear();
    }
}
