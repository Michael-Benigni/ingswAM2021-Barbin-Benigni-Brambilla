package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwapDepotsTest extends ActionTest {
    StorableResource coin1 = new StorableResource(ResourceType.COIN, 1);
    StorableResource stone1 = new StorableResource(ResourceType.STONE, 1);
    StorableResource stone2 = new StorableResource(ResourceType.STONE, 2);

    SwapDepotsTest() throws NegativeResourceAmountException {
    }

    @Test
    void performTest1() throws Exception {
        player1.getPersonalBoard().getWarehouseDepots().store(coin1, 0);
        player1.getPersonalBoard().getWarehouseDepots().store(stone1, 1);
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(0).equals(coin1));
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(1).equals(stone1));
        SwapDepots swapDepotsAction = new SwapDepots(0, 1);
        swapDepotsAction.perform(game, player1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().isEmpty());
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(0).equals(stone1));
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(1).equals(coin1));
    }

    @Test
    void performTest2() throws Exception {
        player1.getPersonalBoard().getWarehouseDepots().store(coin1, 0);
        player1.getPersonalBoard().getWarehouseDepots().store(stone2, 1);
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(0).equals(coin1));
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(1).equals(stone2));
        SwapDepots swapDepotsAction = new SwapDepots(0, 1);
        swapDepotsAction.perform(game, player1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().size() == 1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().get(0).equals(stone1));
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(0).equals(stone1));
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(1).equals(coin1));
    }
}