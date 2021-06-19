package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.LeaderCardNotPlayedException;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.LeaderAction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EndTurnMultiplayerActionTest extends ActionTest {

    @Test
    void perform() throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException,
            WrongInitialConfiguration, NegativeVPAmountException, LeaderCardNotFoundException, EmptySlotException,
            NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException, LeaderCardNotDiscardableException, NullResourceAmountException,
            WrongSlotDevelopmentIndexException, NoValidActionException, YouMustEndTheProductionPhaseException {
        MultiplayerGame.EndTurnMultiplayerAction endTurnMultiplayerAction = new MultiplayerGame.EndTurnMultiplayerAction ();
        Player currentPlayer = game.getCurrentPlayer();
        LeaderAction discardLeader = new LeaderAction("discard", 0);
        for(int i = 0; i < 2; i++){
            try {
                discardLeader.perform(game, currentPlayer);
            } catch (LeaderCardNotPlayedException e) {
                fail ();
            }
        }
        endTurnMultiplayerAction.perform(game, currentPlayer);
        assertFalse(currentPlayer.equals(game.getCurrentPlayer()));
    }
}