package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.EndTurnAction;
import it.polimi.ingsw.server.model.gamelogic.actions.LeaderAction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EndTurnActionTest extends ActionTest {

    @Test
    void perform() throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException,
            WrongInitialConfiguration, NegativeVPAmountException, LeaderCardNotFoundException, EmptySlotException,
            NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException, LeaderCardNotDiscardableException, NullResourceAmountException,
            WrongSlotDevelopmentIndexException, NoValidActionException {
        EndTurnAction endTurnAction = new EndTurnAction();
        Player currentPlayer = game.getCurrentPlayer();
        LeaderAction discardLeader = new LeaderAction("discard", 0);
        for(int i = 0; i < 2; i++){
            discardLeader.perform(game, currentPlayer);
        }
        endTurnAction.perform(game, currentPlayer);
        assertFalse(currentPlayer.equals(game.getCurrentPlayer()));
    }
}