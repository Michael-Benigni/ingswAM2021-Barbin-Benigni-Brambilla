package it.polimi.ingsw.model.personalboard;

import it.polimi.ingsw.exception.DifferentResourceTypeInDepotException;
import it.polimi.ingsw.exception.ResourceOverflowInDepotException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test on the methods of "Depot" class
 */
public class DepotTest {


    /**
     * Test on the constructor method of "Depot" class.
     * Tests if the method creates a depot that contains an empty resource.
     */
    @Test
    void checkConstructorIfCorrect()
    {
        Depot newDepot = new Depot(5);
        assertTrue(newDepot.ifDepotIsEmpty());
    }


    /**
     * Test on "StoreResourceInDepot" method.
     * Tests if the method successfully stores the provided resource in an empty depot.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkStoreResourceInDepotWhenEmpty()
            throws Exception {
        Depot newDepot = new Depot(7);
        StorableResource resourceToStore = new StorableResource(ResourceType.COIN, 5);

        newDepot.storeResourceInDepot(resourceToStore);
        assertTrue(newDepot.getStoredResource().equals(resourceToStore));
    }


    /**
     * Test on "storeResourceInDepot" method
     * Tests if the method successfully add a resource with the same type of the one contained in the depot. Then try to
     * exceeds the capacity of the depot, so the test has to catch the exception.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkStoreResourceInDepotWhenSameType()
            throws Exception {

        Depot newDepot = new Depot(6);

        StorableResource resourceToStore1 = new StorableResource(ResourceType.STONE, 2);
        StorableResource resourceToStore2 = new StorableResource(ResourceType.STONE, 3);
        StorableResource resourceToStore3 = new StorableResource(ResourceType.STONE, 80);
        StorableResource resourceToStoreTot = new StorableResource(ResourceType.STONE, 3+2);

        newDepot.storeResourceInDepot(resourceToStore1);
        newDepot.storeResourceInDepot(resourceToStore2);

        assertTrue(newDepot.getStoredResource().equals(resourceToStoreTot));

        try{
            newDepot.storeResourceInDepot(resourceToStore3);
            fail();
        }catch (ResourceOverflowInDepotException e){
            assertTrue(newDepot.getStoredResource().equals(resourceToStoreTot));
        }

    }


    /**
     * Test on "storeResourceInDepot" method
     * Tests if the method throws successfully an exception when the two resources to store have different type.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkStoreResourceInDepotWhenDifferentType()
            throws Exception {

        Depot newDepot = new Depot(9);

        StorableResource resourceToStore1 = new StorableResource(ResourceType.COIN, 3);
        StorableResource resourceToStore2 = new StorableResource(ResourceType.SERVANT, 4);

        newDepot.storeResourceInDepot(resourceToStore1);

        try{
            newDepot.storeResourceInDepot(resourceToStore2);
            fail();
        }catch (DifferentResourceTypeInDepotException e){
            assertTrue(newDepot.getStoredResource().equals(resourceToStore1));
        }

    }


    /**
     * Test on "removeResourceFromDepot" method.
     * Tests if the method works successfully with resources of the same type.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkRemoveResourceFromDepotIfCorrect()
            throws Exception {

        Depot newDepot = new Depot(16);
        StorableResource resourceToStore = new StorableResource(ResourceType.COIN, 5);
        StorableResource resourceToRemove = new StorableResource(ResourceType.COIN, 2);
        StorableResource resourceToCheck = new StorableResource(ResourceType.COIN, 3);

        newDepot.storeResourceInDepot(resourceToStore);
        newDepot.removeResourceFromDepot(resourceToRemove);

        assertTrue(newDepot.getStoredResource().equals(resourceToCheck));
    }


    /**
     * Test on "removeResourceFromDepot" method.
     * Tests if the method empties completely a depot.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkRemoveResourceFromDepotWhenEmpties()
            throws Exception {
        Depot newDepot = new Depot(16);
        StorableResource resource = new StorableResource(ResourceType.COIN, 5);

        newDepot.storeResourceInDepot(resource);
        newDepot.removeResourceFromDepot(resource);

        assertTrue(newDepot.ifDepotIsEmpty());

    }


    /**
     * Test on "removeResourceFromDepot" method.
     * Test if the method throws the exception successfully when the provided resource has different type than the
     * one stored, and if the method tries to remove any resource from an empty depot.
     * @throws Exception -> can be thrown by "storeResourceInDepot" method.
     */
    @Test
    void checkRemoveResourceFromDepotWhenDifferentType()
            throws Exception {

        Depot newDepot = new Depot(60);
        StorableResource resourceToStore = new StorableResource(ResourceType.STONE, 23);
        StorableResource resourceToRemove = new StorableResource(ResourceType.SHIELD, 21);

        try{
            newDepot.removeResourceFromDepot(resourceToRemove);
            fail();
        }catch (DifferentResourceTypeInDepotException e){
            assertTrue(newDepot.ifDepotIsEmpty());
        }

        newDepot.storeResourceInDepot(resourceToStore);

        try{
            newDepot.removeResourceFromDepot(resourceToRemove);
            fail();
        }catch (DifferentResourceTypeInDepotException e){
            assertTrue(newDepot.getStoredResource().equals(resourceToStore));
        }

    }
}
