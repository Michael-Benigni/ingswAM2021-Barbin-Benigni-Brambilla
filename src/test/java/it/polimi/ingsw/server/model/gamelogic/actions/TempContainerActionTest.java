package it.polimi.ingsw.server.model.gamelogic.actions;


import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TempContainerActionTest extends ActionTest {
    StorableResource resource = new StorableResource(ResourceType.COIN, 1);

    TempContainerActionTest() throws NegativeResourceAmountException {
    }

    @Test
    void performStoreTest() throws WrongDepotIndexException, NegativeResourceAmountException, EmptyDepotException,
            SameResourceTypeInDifferentDepotsException, NotEqualResourceTypeException, ResourceOverflowInDepotException,
            NotContainedResourceException, TempContainerForProductionException {
        player1.getPersonalBoard().getWarehouseDepots().store(resource, 0);
        TempContainerAction tempContainerAction = new TempContainerAction("store", resource, 0);
        tempContainerAction.perform(game, player1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().get(0).equals(resource));
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().isEmpty());
    }

    @Test
    void performRemoveTest() throws WrongDepotIndexException, NegativeResourceAmountException, EmptyDepotException,
            SameResourceTypeInDifferentDepotsException, NotEqualResourceTypeException, ResourceOverflowInDepotException,
            NotContainedResourceException, TempContainerForProductionException {
        player1.getPersonalBoard().getTempContainer().store(resource);
        TempContainerAction tempContainerAction = new TempContainerAction("remove", resource, 0);
        tempContainerAction.perform(game, player1);
        assertTrue(player1.getPersonalBoard().getWarehouseDepots().getAllResources().get(0).equals(resource));
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().isEmpty());
    }
}