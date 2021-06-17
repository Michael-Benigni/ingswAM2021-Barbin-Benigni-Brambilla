package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.exception.WrongDepotIndexException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on "WarehouseDepots" class.
 */
public class WarehouseDepotsTest {

    /**
     * Test on the constructor method.
     * Tests if the method creates an empty warehouse.
     */
    @Test
    void checkConstructorIfCorrect() {
        WarehouseDepots newWarehouseDepots = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(1, 2, 3)));
        assertEquals(newWarehouseDepots.getAllResources(), new ArrayList<StorableResource>(0));
    }

    /**
     * Test on "store" method.
     * Tests if the method correctly inserts, in the created strongbox, a resource.
     * @throws Exception can be thrown by "store" method.
     */
    @Test
    void checkStoreIfCorrect() throws Exception {
        WarehouseDepots newWarehouseDepots = new WarehouseDepots(2, new ArrayList<>(Arrays.asList(7, 17)));
        StorableResource resourceToStore1 = new StorableResource(ResourceType.STONE, 5);
        StorableResource resourceToStore2 = new StorableResource(ResourceType.SERVANT, 17);
        newWarehouseDepots.store(resourceToStore1, 0);
        newWarehouseDepots.store(resourceToStore2, 1);
        ArrayList<StorableResource> list = new ArrayList<>(0);
        list.add(resourceToStore1);
        list.add(resourceToStore2);
        assertEquals(list, newWarehouseDepots.getAllResources());
    }

    /**
     * Test on "store" method of this class.
     * It Tests if the method throws successfully the exception when the provided index is less than zero or exceeds the
     * number of depots.
     * @throws Exception can be thrown by "store" method.
     */
    @Test
    void checkStoreIfWrongIndex() throws Exception {

        WarehouseDepots newWarehouseDepots = new WarehouseDepots(4, new ArrayList<>(Arrays.asList(10, 14, 20, 6)));
        StorableResource resourceToStore = new StorableResource(ResourceType.COIN, 7);
        try {
            newWarehouseDepots.store(resourceToStore, -3);
        }catch (WrongDepotIndexException e){
            assertEquals(newWarehouseDepots.getAllResources(), new ArrayList<StorableResource>(0));
        }
        try {
            newWarehouseDepots.store(resourceToStore, 55);
        }catch (WrongDepotIndexException e){
            assertEquals(newWarehouseDepots.getAllResources(), new ArrayList<StorableResource>(0));
        }
    }

    /**
     * Test on "remove" method of this class.
     * Tests if the method works correctly.
     * @throws Exception can be thrown by "store" method.
     */
    @Test
    void checkRemoveIfCorrect() throws Exception {
        WarehouseDepots newWarehouseDepots = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(20, 21, 20)));
        StorableResource resourceToStore1 = new StorableResource(ResourceType.SHIELD, 8);
        StorableResource resourceToStore2 = new StorableResource(ResourceType.COIN, 16);
        StorableResource resourceToRemove1 = new StorableResource(ResourceType.SHIELD, 5);
        StorableResource resourceRemained = new StorableResource(ResourceType.SHIELD, 3);
        newWarehouseDepots.store(resourceToStore1,0);
        newWarehouseDepots.store(resourceToStore2,1);
        newWarehouseDepots.remove(resourceToRemove1,0);
        newWarehouseDepots.remove(resourceToStore2,1);
        ArrayList<StorableResource> listOfResource = new ArrayList<>(0);
        listOfResource.add(resourceRemained);
        assertEquals(newWarehouseDepots.getAllResources(), listOfResource);
    }

    /**
     * Test on "addExtraDepot" method of this class.
     * It tests if the method add a new depot (containing a resource with amount equal to zero) in the warehouse.
     * @throws Exception can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void checkAddExtraDepot() throws Exception {
        WarehouseDepots newWarehouseDepots = new WarehouseDepots(2, new ArrayList<>(Arrays.asList(4, 10)));
        newWarehouseDepots.addExtraDepot(10, ResourceType.COIN);
        ArrayList<StorableResource> newResource = new ArrayList<>(0);
        newResource.add(new StorableResource(ResourceType.COIN, 0));
        assertNotEquals(newWarehouseDepots.getAllResources(), newResource);
        assertEquals (newWarehouseDepots.getAllResources (), new ArrayList<> ());
    }

    /**
     * Test on "swapDepots" method of this class.
     * It tests if the method successfully switch the two depots.
     * @throws Exception
     */
    @Test
    void checkSwapDepotsIfCorrect() throws Exception {
        WarehouseDepots swappedWarehouse = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(40, 26, 56)));
        StorableResource coin = new StorableResource(ResourceType.COIN, 4);
        StorableResource stone = new StorableResource(ResourceType.STONE, 10);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 8);
        swappedWarehouse.store(coin, 0);
        swappedWarehouse.store(stone, 1);
        swappedWarehouse.store(shield, 2);
        swappedWarehouse.swapDepots(0, 1);
        WarehouseDepots newWarehouse = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(40, 26, 56)));
        newWarehouse.store(stone, 0);
        newWarehouse.store(coin, 1);
        newWarehouse.store(shield, 2);
        assertEquals(swappedWarehouse, newWarehouse);
    }

    /**
     * Test on "swapDepots" method of this class.
     * It tests if the method throws successfully an exception when one of the provided index is negative or exceeds
     * the number of depots.
     * @throws Exception can be thrown by "swapDepot" method of this class.
     */
    @Test
    void checkSwapDepotsIfWrongIndex() throws Exception {
        WarehouseDepots newWarehouse = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(5, 6, 100)));
        try {
            newWarehouse.swapDepots(1, 16);
            fail();
        } catch (WrongDepotIndexException e) {
            assertEquals(newWarehouse, new WarehouseDepots(3, new ArrayList<>(Arrays.asList(5, 6, 100))));
        }
        try {
            newWarehouse.swapDepots(-5, 2);
            fail();
        } catch(WrongDepotIndexException e) {
            assertEquals(newWarehouse, new WarehouseDepots(3, new ArrayList<>(Arrays.asList(5, 6, 100))));
        }
    }

    /**
     * Test on "swapDepots" method of this class.
     * It tests if the method returns a resource that has amount equal to the amount that exceed the capacity of that depot.
     * @throws Exception can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void checkSwapDepotsIfResourcesExceed() throws Exception {
        WarehouseDepots newWarehouse = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(5, 9, 100)));
        StorableResource coin = new StorableResource(ResourceType.COIN, 4);
        StorableResource stone = new StorableResource(ResourceType.STONE, 8);
        StorableResource stoneInExceeding = new StorableResource(ResourceType.STONE, 3);
        newWarehouse.store(coin, 0);
        newWarehouse.store(stone, 1);
        StorableResource stoneFromWarehouse = newWarehouse.swapDepots(0, 1);
        assertEquals(stoneFromWarehouse, stoneInExceeding);
        WarehouseDepots warehouseToBeCompared = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(5, 9, 100)));
        warehouseToBeCompared.store(coin, 1);
        warehouseToBeCompared.store(new StorableResource(ResourceType.STONE, 5), 0);
        assertEquals(warehouseToBeCompared, newWarehouse);
    }
}
