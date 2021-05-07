package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;

class DiscardAllResources implements Action {

    @Override
    public void perform(Game game, Player player) throws Exception {
        FaithPoint points = player.getPersonalBoard().getTempContainer().getPenalty();
        for(Player p : game.getAllPlayers()) {
            if (!(p == player))
                points.activate(p, game);
        }
        player.getPersonalBoard().getTempContainer().clear();
    }
}
