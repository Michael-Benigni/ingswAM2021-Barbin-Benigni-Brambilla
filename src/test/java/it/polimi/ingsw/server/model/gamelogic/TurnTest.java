package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import org.junit.jupiter.api.Test;

class TurnTest {
    private Turn turn = new Turn();
    private Action alwaysValid ;

    @Test
    void add() {
        /*try {
            turn.add(alwaysValid);
        } catch (NoValidActionException e) {
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            assertTrue(true);
        }
        try {
            turn.add(startAction);
        } catch (NoValidActionException e) {
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            assertTrue(true);
        }*/
    }

    @Test
    void addAfterStart() {
        /*turn.start();
        try {
            turn.add(alwaysValid);
            assertTrue(true);
        } catch (NoValidActionException e) {
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        }
        try {
            turn.add(startAction);
        } catch (NoValidActionException e) {
            assertTrue(true);
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        }*/
    }

    @Test
    void start() {
    }

    @Test
    void terminate() {
    }

    @Test
    void addUndoableAction() {
    }

    @Test
    void clearCache() {
    }

    @Test
    void undo() {
    }
}