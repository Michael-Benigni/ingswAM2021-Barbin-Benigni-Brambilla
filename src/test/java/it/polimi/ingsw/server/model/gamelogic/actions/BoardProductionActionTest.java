package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardProductionActionTest extends ActionTest {
    StorableResource coin1 = new StorableResource(ResourceType.COIN, 1);
    StorableResource stone2 = new StorableResource(ResourceType.STONE, 2);
    StorableResource servant1 = new StorableResource(ResourceType.SERVANT, 1);
    StrongboxAction pay2stone = new StrongboxAction(PayAction.StoreOrRemove.REMOVE, stone2);
    StrongboxAction pay1stone = new StrongboxAction(PayAction.StoreOrRemove.REMOVE, new StorableResource (ResourceType.STONE, 1));
    WarehouseAction pay1servant = new WarehouseAction(PayAction.StoreOrRemove.REMOVE, servant1, 1);
    ArrayList<PayAction> payActions = new ArrayList<>();
    BoardProductionAction boardProductionAction;

    BoardProductionActionTest() throws NegativeResourceAmountException {
    }

    void buildProductionTest() throws Exception {
        player1.getPersonalBoard().getStrongbox().store(stone2);
        player1.getPersonalBoard().getWarehouseDepots().store(servant1, 1);
        payActions.add(pay1stone);
        payActions.add(pay1servant);
        StartProductionAction startProductionAction = new StartProductionAction();
        startProductionAction.perform(game, player1);
        boardProductionAction = new BoardProductionAction(coin1, payActions);
        boardProductionAction.perform(game, player1);
    }

    @Test
    void performTest() throws Exception {
        buildProductionTest();
        ArrayList<StorableResource> resourcesFromTempContainer = player1.getPersonalBoard().getTempContainer().getAllResources();
        assertEquals(resourcesFromTempContainer.get(0), coin1);
        try {
            boardProductionAction.perform(game, player1);
            fail();
        }
        catch (AlreadyUsedForProductionException e){
            assertTrue(true);
        }
    }
}
