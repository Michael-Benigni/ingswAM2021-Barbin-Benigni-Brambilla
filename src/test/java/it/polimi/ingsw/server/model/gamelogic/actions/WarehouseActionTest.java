package it.polimi.ingsw.server.model.gamelogic.actions;


import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseActionTest extends ActionTest {

    @Test
    void performTest1() throws NegativeResourceAmountException, WrongDepotIndexException, NotEqualResourceTypeException, ResourceOverflowInDepotException, EmptyDepotException, SameResourceTypeInDifferentDepotsException {
        StorableResource stone = new StorableResource(ResourceType.STONE, 2);
        StorableResource coin = new StorableResource(ResourceType.COIN, 2);
        WarehouseAction warehouseAction1 = new WarehouseAction(PayAction.StoreOrRemove.STORE, stone, 2);
        warehouseAction1.perform(game, player1);
        ArrayList<StorableResource> resourcesFromWarehouse = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertEquals (stone, resourcesFromWarehouse.get (0));

        WarehouseAction warehouseAction2 = new WarehouseAction(PayAction.StoreOrRemove.STORE, coin, 1);
        ArrayList<StorableResource> listOfAddedResources = new ArrayList<>();
        warehouseAction2.perform(game, player1);
        resourcesFromWarehouse = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertEquals (resourcesFromWarehouse.get (0), coin);

        WarehouseAction warehouseAction3 = new WarehouseAction(PayAction.StoreOrRemove.REMOVE, stone, 2);
        warehouseAction3.perform(game, player1);
        resourcesFromWarehouse = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        listOfAddedResources.add(coin);
        assertEquals (resourcesFromWarehouse, listOfAddedResources);
    }

    @Test
    void performTest2() throws NegativeResourceAmountException {
        StorableResource resource2 = new StorableResource(ResourceType.STONE, 2);
        StorableResource resource3 = new StorableResource(ResourceType.SERVANT, 3);
        StorableResource resource4 = new StorableResource(ResourceType.SHIELD, 4);
        WarehouseAction warehouseAction1 = new WarehouseAction(PayAction.StoreOrRemove.STORE, resource2, 0);
        WarehouseAction warehouseAction2 = new WarehouseAction(PayAction.StoreOrRemove.STORE, resource3, 1);
        WarehouseAction warehouseAction3 = new WarehouseAction(PayAction.StoreOrRemove.STORE, resource4, 2);
        try{
            warehouseAction1.perform(game, player1);
            fail();
        } catch (WrongDepotIndexException | SameResourceTypeInDifferentDepotsException | EmptyDepotException | NotEqualResourceTypeException e) {
            fail();
        } catch (ResourceOverflowInDepotException e) {
            assertTrue(true);
        }
        ArrayList<StorableResource> resourcesFromDepots = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertEquals (resourcesFromDepots.get (0), new StorableResource (ResourceType.STONE, capacities.get (0)));

        try{
            warehouseAction2.perform(game, player1);
            fail();
        } catch (WrongDepotIndexException | SameResourceTypeInDifferentDepotsException | EmptyDepotException | NotEqualResourceTypeException e) {
            fail();
        } catch (ResourceOverflowInDepotException e) {
            assertTrue(true);
        }
        resourcesFromDepots = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertEquals (resourcesFromDepots.get (1), new StorableResource (ResourceType.SERVANT, capacities.get (1)));

        try{
            warehouseAction3.perform(game, player1);
            fail();
        } catch (WrongDepotIndexException | SameResourceTypeInDifferentDepotsException | EmptyDepotException | NotEqualResourceTypeException e) {
            fail();
        } catch (ResourceOverflowInDepotException e) {
            assertTrue(true);
        }
        resourcesFromDepots = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertEquals (resourcesFromDepots.get (2), new StorableResource (ResourceType.SHIELD, capacities.get (2)));

        ArrayList<StorableResource> resourcesFromTempContainer = player1.getPersonalBoard().getTempContainer().getAllResources();
    }


    @Test
    void getUndoActionTest() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.SERVANT, 1);
        WarehouseAction warehouseAction1 = new WarehouseAction(PayAction.StoreOrRemove.STORE, resource, 2);
        WarehouseAction warehouseAction2 = new WarehouseAction(PayAction.StoreOrRemove.REMOVE, resource, 2);
        Action action1 = warehouseAction1.getUndoAction();
        Action action2 = warehouseAction2.getUndoAction();
        assertEquals (warehouseAction2, action1);
        assertEquals (warehouseAction1, action2);
    }

    @Test
    void getResourceTest() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.SERVANT, 1);
        WarehouseAction warehouseAction = new WarehouseAction(PayAction.StoreOrRemove.STORE, resource, 2);
        StorableResource resource1 = warehouseAction.getResource();
        assertEquals (resource, resource1);
    }
}