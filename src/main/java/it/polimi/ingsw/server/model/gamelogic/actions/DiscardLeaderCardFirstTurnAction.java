package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

public class DiscardLeaderCardFirstTurnAction implements FirstTurnAction {
    private final int cardIndex;

    public DiscardLeaderCardFirstTurnAction(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     *
     * @param game   -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    @Override
    public void perform(Game game, Player player) throws Exception {
        LeaderCard card = player.getPersonalBoard ().getSlotLeaderCards ().get (cardIndex);
        player.getPersonalBoard ().getSlotLeaderCards ().remove (card);
    }
}
