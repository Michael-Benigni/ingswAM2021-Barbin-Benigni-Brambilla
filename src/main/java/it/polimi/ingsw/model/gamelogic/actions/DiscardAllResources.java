package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;

class DiscardAllResources extends Action {
    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
    }


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
