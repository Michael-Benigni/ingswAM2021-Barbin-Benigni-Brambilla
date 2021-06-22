package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalTurnState;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.server.model.gamelogic.actions.PayAction;
import it.polimi.ingsw.server.model.gamelogic.actions.StrongboxAction;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for "FirstTurn" class.
 */
public class FirstTurnTest {

    /**
     * Test on "add" method of this class.
     * It tests if the method throws an exception when trying to add an action to a first turn.
     */
    @Test
    void checkAddIfCorrect() throws NegativeResourceAmountException {
        FirstTurn firstTurn = new FirstTurn();
        Action action = new StrongboxAction(PayAction.StoreOrRemove.STORE, new StorableResource(ResourceType.COIN, 1));
        try {
            firstTurn.add(action);
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        }
    }
}
