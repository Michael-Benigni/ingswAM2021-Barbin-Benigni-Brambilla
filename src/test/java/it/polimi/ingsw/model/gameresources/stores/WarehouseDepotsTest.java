package it.polimi.ingsw.model.gameresources.stores;


import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests on "WarehouseDepots" class.
 */
public class WarehouseDepotsTest {


    /**
     * Test on the constructor method.
     * Tests if the method creates an empty warehouse successfully from the json file in the database
     */
    @Test
    void checkConstructorIfCorrect() throws FileNotFoundException {
        WarehouseDepots newWarehouseDepots = new WarehouseDepots();
        assertTrue(newWarehouseDepots.ifWarehouseIsEmpty());
        int numberOfDepots = newWarehouseDepots.getNumberOfDepots();
        assertTrue(numberOfDepots == 3);
        for(int i = 0; i < numberOfDepots; i++){
            int capacity = newWarehouseDepots.getDepotCapacity(i);
            assertTrue(capacity == (i + 1));
        }
    }//Quando hai checkato ricordati di eliminare i seguenti metodi: "getNumberOfDepots" e "getDepotCapacity" da "WarehouseDepots"; "getCapacity" da Depot.


    /**
     * Test on "storeResourceInWarehouse" method.
     * Tests if the method correctly inserts, in the created strongbox, a resource.
     * @throws Exception -> can be thrown by "storeResourceInWarehouse" method.
     */
    @Test
    void checkStoreResourceIfCorrect()
            throws Exception {

        WarehouseDepots newWarehouseDepots = new WarehouseDepots(15, 21);
        StorableResource resourceToStore1 = new StorableResource(ResourceType.STONE, 5);
        StorableResource resourceToStore2 = new StorableResource(ResourceType.SERVANT, 17);

        newWarehouseDepots.storeResourceInWarehouse(resourceToStore1, 0);
        assertTrue(newWarehouseDepots.getResourceFromDepot(0).equals(resourceToStore1));

        newWarehouseDepots.storeResourceInWarehouse(resourceToStore2, 1);
        assertTrue(newWarehouseDepots.getResourceFromDepot(1).equals(resourceToStore2));
    }


    /**
     * Test on "storeResourceInWarehouse" method.
     * Tests if the method throws successfully the exception when the provided index is less than zero or exceeds the
     * number of depots.
     * @throws Exception -> can be thrown by "storeResourceInWarehouse" method.
     */
    @Test
    void checkStoreResourceIfWrongIndex()
            throws Exception {

        WarehouseDepots newWarehouseDepots = new WarehouseDepots(12, 18);
        StorableResource resourceToStore = new StorableResource(ResourceType.COIN, 7);

        try {
            newWarehouseDepots.storeResourceInWarehouse(resourceToStore, -3);
        }catch (WrongDepotIndexException e){
            assertTrue(newWarehouseDepots.ifWarehouseIsEmpty());
        }
        try {
            newWarehouseDepots.storeResourceInWarehouse(resourceToStore, 55);
        }catch (WrongDepotIndexException e){
            assertTrue(newWarehouseDepots.ifWarehouseIsEmpty());
        }
    }


    /**
     * Test on "removeResourceFromWarehouse" method.
     * Tests if the method works correctly.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkRemoveResourceIfCorrect()
            throws Exception {

        WarehouseDepots newWarehouseDepots = new WarehouseDepots(9, 25);
        StorableResource resourceToStore1 = new StorableResource(ResourceType.SHIELD, 8);
        StorableResource resourceToStore2 = new StorableResource(ResourceType.COIN, 16);

        StorableResource resourceToRemove1 = new StorableResource(ResourceType.SHIELD, 5);
        StorableResource resourceToRemove2 = new StorableResource(ResourceType.COIN, 16);
        StorableResource resourceRemained = new StorableResource(ResourceType.SHIELD, 3);

        newWarehouseDepots.storeResourceInWarehouse(resourceToStore1,0);
        newWarehouseDepots.storeResourceInWarehouse(resourceToStore2,1);
        newWarehouseDepots.removeResourceFromWarehouse(resourceToRemove1,0);
        newWarehouseDepots.removeResourceFromWarehouse(resourceToRemove2,1);

        assertTrue(newWarehouseDepots.getResourceFromDepot(0).equals(resourceRemained));
        assertTrue(newWarehouseDepots.getResourceFromDepot(1) == null);
    }

}
