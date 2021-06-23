package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.TemporaryContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*class EndProductionActionTest extends ActionTest {
    private BoardProductionActionTest boardProductionActionTest = new BoardProductionActionTest();

    EndProductionActionTest() throws NegativeResourceAmountException {
    }

    @Test
    void performTest() throws Exception {
        game.setNextPlayer();
        player1.getPersonalBoard().getStrongbox().store(boardProductionActionTest.stone2);
        player1.getPersonalBoard().getWarehouseDepots().store(boardProductionActionTest.servant1, 1);
        boardProductionActionTest.payActions.add(boardProductionActionTest.pay2stone);
        boardProductionActionTest.payActions.add(boardProductionActionTest.pay1servant);
        StartProductionAction startProductionAction = new StartProductionAction();
        startProductionAction.perform(game, player1);
        BoardProductionAction boardProductionAction = new BoardProductionAction(boardProductionActionTest.coin1, boardProductionActionTest.payActions);
        boardProductionAction.perform(game, player1);
        EndProductionAction endProductionAction = new EndProductionAction();
        endProductionAction.perform(game, player1);
        assertEquals(player1.getPersonalBoard().getStrongbox().getAllResources().get(0), boardProductionActionTest.coin1);
    }
}*/