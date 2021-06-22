package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.exception.EmptyDepotException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.model.exception.ResourceOverflowInDepotException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for "ExtraDepot" class.
 */
public class ExtraDepotTest {

    /**
     * Test on "storeResourceInDepot" method of this class.
     * It tests if the method successfully stores the resources inside the depot.
     */
    @Test
    void checkStoreIfCorrect() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(2, ResourceType.COIN);
        try {
            extraDepot.storeResourceInDepot(new StorableResource(ResourceType.COIN, 1));
            assertEquals(extraDepot.getStoredResource(), new StorableResource(ResourceType.COIN, 1));
        } catch (NotEqualResourceTypeException | ResourceOverflowInDepotException | EmptyDepotException e) {
            fail();
        }

    }

    /**
     * Test on "storeResourceInDepot" method of this class.
     * It tests if the method successfully accepts to store one nullified resource, and "getStoredResource" should throws an exception.
     */
    @Test
    void checkStoreIfNull() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(2, ResourceType.STONE);
        try {
            extraDepot.storeResourceInDepot(null);
        } catch (NotEqualResourceTypeException | ResourceOverflowInDepotException e) {
            fail();
        }
        try {
            extraDepot.getStoredResource();
            fail();
        } catch (EmptyDepotException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "storeResourceInDepot" method of this class.
     * It tests if the method successfully throws an exception if trying to store a resource with different type of the one stored.
     */
    @Test
    void checkStoreIfDifferentType() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(5, ResourceType.COIN);
        try {
            extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 2));
            fail();
        } catch (NotEqualResourceTypeException e) {
            try {
                extraDepot.getStoredResource();
                fail();
            } catch (EmptyDepotException emptyDepotException) {
                assertTrue(true);
            }
        } catch (ResourceOverflowInDepotException e) {
            fail();
        }
    }

    /**
     * Test on "storeResourceInDepot" method of this class.
     * It tests if the method successfully throws an exception when exceeding the capacity of this extra depot.
     */
    @Test
    void checkStoreIfOverflow() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(3, ResourceType.STONE);
        try {
            extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 100));
            fail();
        } catch (NotEqualResourceTypeException e) {
            fail();
        } catch (ResourceOverflowInDepotException e) {
            try {
                assertEquals(extraDepot.getStoredResource(), new StorableResource(ResourceType.STONE, 3));
            } catch (EmptyDepotException emptyDepotException) {
                fail();
            }
        }
    }

    /**
     * Test on "clear" method of this class.
     * It tests if the method successfully empties this extra depot.
     */
    @Test
    void checkClear() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(3, ResourceType.STONE);
        try {
            extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 3));
            try {
                assertEquals(extraDepot.getStoredResource(), new StorableResource(ResourceType.STONE, 3));
            } catch (EmptyDepotException e) {
                fail();
            }
            extraDepot.clear();
            try {
                extraDepot.getStoredResource();
                fail();
            } catch (EmptyDepotException e) {
                assertTrue(true);
            }
        } catch (NotEqualResourceTypeException | ResourceOverflowInDepotException e) {
            fail();
        }
    }

    /**
     * Test on "removeResourceFromDepot" method of this class.
     * It tests if the method successfully decreases the amount of the stored resource.
     */
    @Test
    void checkRemoveIfCorrect() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(3, ResourceType.STONE);
        extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 3));
        try {
            extraDepot.removeResourceFromDepot(new StorableResource(ResourceType.STONE, 1));
            assertEquals(extraDepot.getStoredResource(), new StorableResource(ResourceType.STONE, 2));
        } catch (NotEqualResourceTypeException | EmptyDepotException e) {
            fail();
        }
    }

    /**
     * Test on "removeResourceFromDepot" method of this class.
     * It tests if the method successfully throws an exception when trying to remove a resource with a different type.
     */
    @Test
    void checkRemoveIfDifferentType() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException, EmptyDepotException {
        ExtraDepot extraDepot = new ExtraDepot(3, ResourceType.STONE);
        extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 3));
        try {
            extraDepot.removeResourceFromDepot(new StorableResource(ResourceType.SERVANT, 1));
            fail();
        } catch (NotEqualResourceTypeException e) {
            assertEquals(extraDepot.getStoredResource(), new StorableResource(ResourceType.STONE, 3));
        } catch (EmptyDepotException e) {
            fail();
        }
    }

    /**
     * Test on "removeResourceFromDepot" method of this class.
     * It tests if the method successfully empties this extra depot.
     */
    @Test
    void checkRemoveIfEmpties() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(3, ResourceType.STONE);
        extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 3));
        try {
            extraDepot.removeResourceFromDepot(extraDepot.getStoredResource());
            try {
                extraDepot.getStoredResource();
                fail();
            } catch(EmptyDepotException e) {
                assertTrue(true);
            }
        } catch (NegativeResourceAmountException | NotEqualResourceTypeException | EmptyDepotException e) {
            fail();
        }
    }

    /**
     * Test on "removeResourceFromDepot" method of this class.
     * It tests if the method successfully throws an exception if trying to decrease the amount of the stored resource by a greater amount.
     */
    @Test
    void checkRemoveIfNegativeAmount() throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(3, ResourceType.STONE);
        extraDepot.storeResourceInDepot(new StorableResource(ResourceType.STONE, 3));
        try {
            extraDepot.removeResourceFromDepot(new StorableResource(ResourceType.STONE, 100));
            fail();
        } catch (NotEqualResourceTypeException | EmptyDepotException e) {
            fail();
        } catch (NegativeResourceAmountException e) {
            try {
                assertEquals(extraDepot.getStoredResource(), new StorableResource(ResourceType.STONE, 3));
            } catch (EmptyDepotException emptyDepotException) {
                fail();
            }
            assertEquals(e.getRemainder(), new StorableResource(ResourceType.STONE, 97));
        }
    }
}
