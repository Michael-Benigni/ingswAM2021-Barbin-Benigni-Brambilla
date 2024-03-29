package it.polimi.ingsw.server.model.gamelogic.actions;


import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.exception.LeaderCardNotPlayedException;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

public class LeaderAction implements Action {
    private final String playOrDiscard;
    private final int numInSlot;

    public LeaderAction(String playOrDiscard, int numInSlot) {
        this.playOrDiscard = playOrDiscard;
        this.numInSlot = numInSlot;
    }

    @Override
    public void perform(Game game, Player player) throws LeaderCardNotDiscardableException, EmptySlotException,
            NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException, NullResourceAmountException, WrongSlotDevelopmentIndexException,
            WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException,
            LeaderCardNotFoundException, NoValidActionException, LeaderCardNotPlayedException {
        LeaderCard card = player.getPersonalBoard().getSlotLeaderCards().get(numInSlot);
        switch (playOrDiscard) {
            case "play" : {
                card.play(player, game);
                return;
            }
            case "discard": {
                card.onDiscarded().activate(player, game);
                player.getPersonalBoard().getSlotLeaderCards().remove(card);
                return;
            }
            default:
                throw new NoValidActionException(this);
        }
    }
}
