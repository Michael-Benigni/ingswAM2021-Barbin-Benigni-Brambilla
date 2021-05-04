package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;

class TrasformWhiteMarbleAction extends Action {
    private final int resourceIndex;

    public TrasformWhiteMarbleAction(int resourceIndex) {
        this.resourceIndex = resourceIndex;
    }

    /**
     * @return the type of the Action
     */
    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
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
