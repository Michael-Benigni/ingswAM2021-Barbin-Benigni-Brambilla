package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

class TransformWhiteMarbleAction implements Action {
    private final int resourceIndex;

    TransformWhiteMarbleAction(int resourceIndex) {
        this.resourceIndex = resourceIndex;
    }


    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game the Game on which this Action will be performed
     * @param player the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        player.getPersonalBoard().getTempContainer().transformEmptyResources(player, this.resourceIndex);
    }
}
