package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;

public class DiscardAllResources extends Action {
    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        FaithPoint points = player.getPersonalBoard().getTempContainer().discardAll();
        for(Player p : game.getAllPlayers()) {
            if (!p.equals(player))
                points.activate(p, game);
        }
    }
}
