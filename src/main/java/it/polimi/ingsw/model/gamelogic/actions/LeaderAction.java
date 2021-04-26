package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;

class LeaderAction extends Action {
    private final String playOrDiscard;
    private final int numInSlot;

    public LeaderAction(String playOrDiscard, int numInSlot) {
        this.playOrDiscard = playOrDiscard;
        this.numInSlot = numInSlot;
    }

    @Override
    protected ActionType getType() {
        return ActionType.ANYTIME;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        LeaderCard card = player.getPersonalBoard().getSlotLeaderCards().get(numInSlot);
        switch (playOrDiscard) {
            case "play" : {
                card.play(player, game);
                return;
            }
            case "discard": {
                card.onDiscarded().activate(player, game);
                return;
            }
            default:
        }
    }
}
