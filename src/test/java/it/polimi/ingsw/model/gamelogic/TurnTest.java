package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.exception.IllegalTurnState;
import it.polimi.ingsw.exception.NoValidActionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class TurnTest {
    private Turn turn = new Turn();
    private Action alwaysValid ;
    private Action startAction = new StartTurnAction();

    @Test
    void add() {
        try {
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
        }
    }

    @Test
    void addAfterStart() {
        turn.start();
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
        }
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