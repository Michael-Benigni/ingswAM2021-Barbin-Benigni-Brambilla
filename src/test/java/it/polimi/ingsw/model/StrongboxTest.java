package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotContainedResourceException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class StrongboxTest {


    /**
     * Test on the constructor method
     * Test if the created object contains a nullified array
     */
    @Test
    void checkConstructorIfCorrect()
    {
        Strongbox newStrongbox = new Strongbox();
        assertInstanceOf(Strongbox.class, newStrongbox);
        assertTrue(newStrongbox.ifEmptyStrongbox());
    }


    /**
     * Test on the "storeResourceInStrongbox" method
     * Test if the method put a new resource in an empty strongbox
     * @throws NegativeResourceAmountException -> can be throwed by constructor method of "StorableResource" class
     * @throws NotEqualResourceTypeException -> can be throwed by "storeResourceInStrongbox" method
     */
    @Test
    void checkStoreProductedResourceIfAddResourceWhenEmpty()
            throws NegativeResourceAmountException, NotEqualResourceTypeException, NotContainedResourceException {

        Strongbox newStrongbox = new Strongbox();

        int amountOfCoin = 80;
        StorableResource resourceCoin = new StorableResource(ResourceType.COIN, amountOfCoin);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceCoin);

        newStrongbox.storeResourceInStrongbox(resourceCoin);

        assertFalse(newStrongbox.ifEmptyStrongbox());

        ArrayList<StorableResource> resourceStrongbox = newStrongbox.getAllStoredResources();
        assertEquals(resourceStrongbox, resourceArray);
    }


    /**
     * Test on "storeResourceInStrongbox" method
     * Test if the method put a new resource in a strongbox that already contains a resource with different type
     * @throws NotEqualResourceTypeException -> can be throwed by constructor method of "StorableResource" class
     * @throws NegativeResourceAmountException -> can be throwed by "storeResourceInStrongbox" method
     */
    @Test
    void checkStoreProductedResourceIfNotAlreadyContained()
            throws NotEqualResourceTypeException, NegativeResourceAmountException, NotContainedResourceException {

        Strongbox newStrongbox = new Strongbox();

        int amountOfStone = 45;
        StorableResource resourceStone = new StorableResource(ResourceType.STONE, amountOfStone);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceStone);

        int amountOfShield = 18;
        StorableResource resourceShield = new StorableResource(ResourceType.SHIELD, amountOfShield);
        resourceArray.add(resourceShield);

        newStrongbox.storeResourceInStrongbox(resourceStone);
        newStrongbox.storeResourceInStrongbox(resourceShield);

        ArrayList<StorableResource> resourceStrongbox = newStrongbox.getAllStoredResources();
        assertEquals(resourceStrongbox, resourceArray);
    }


    /**
     * Test on "storeResourceInStrongbox" method
     * Test if the method increases the amount of a contained resource correctly
     * @throws NotEqualResourceTypeException -> can be throwed by constructor method of "StorableResource" class
     * @throws NegativeResourceAmountException -> can be throwed by "storeResourceInStrongbox" method
     * @throws NotContainedResourceException -> can be throwed by "storeResourceInStrongbox"
     */
    @Test
    void checkStoreProductedResourceIfAlreadyContained()
            throws NotEqualResourceTypeException, NegativeResourceAmountException, NotContainedResourceException {

        int amountOfCoin1 = 5;
        int amountOfCoin2 = 13;
        int amountTotalCoin = amountOfCoin1 + amountOfCoin2;

        StorableResource resourceCoin1 = new StorableResource(ResourceType.COIN, amountOfCoin1);
        StorableResource resourceCoin2 = new StorableResource(ResourceType.COIN, amountOfCoin2);
        StorableResource resourceTotalCoin = new StorableResource(ResourceType.COIN, amountTotalCoin);

        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceTotalCoin);

        Strongbox newStrongbox = new Strongbox();
        newStrongbox.storeResourceInStrongbox(resourceCoin1);
        newStrongbox.storeResourceInStrongbox(resourceCoin2);

        ArrayList<StorableResource> resourceStrongbox = newStrongbox.getAllStoredResources();
        assertTrue(resourceArray.get(0).equals(resourceStrongbox.get(0)));

    }


    /**
     * Test on "removeResourceFromStrongbox" method
     * Test if the method decrease the amount of the stored resource correctly
     * @throws NegativeResourceAmountException -> can be throwed by constructor method of "StorableResource" class
     * @throws NotEqualResourceTypeException -> can be throwed by "storeResourceInStrongbox" method
     * @throws NotContainedResourceException -> can be throwed by "storeResourceInStrongbox" method
     */
    @Test
    void checkRemoveResourceFromStrongboxIfCorrect()
            throws NegativeResourceAmountException, NotEqualResourceTypeException, NotContainedResourceException {

        Strongbox newStrongbox = new Strongbox();

        StorableResource resourceCoin1 = new StorableResource(ResourceType.COIN, 10);
        StorableResource resourceCoin2 = new StorableResource(ResourceType.COIN, 7);
        StorableResource resourceCoin3 = new StorableResource(ResourceType.COIN, 3);

        newStrongbox.storeResourceInStrongbox(resourceCoin1);
        newStrongbox.removeResourceFromStrongbox(resourceCoin2);

        assertTrue(newStrongbox.getAllStoredResources().get(0).equals(resourceCoin3));
    }


    /**
     * Test on "removeResourceFromStrongbox" method
     * Test if the method throws the NotContainedResourceException successfully
     * @throws NotEqualResourceTypeException -> can be throwed by "decreaseAmount" method
     * @throws NotContainedResourceException -> can be throwed by "removeResourceFromStrongbox" method
     * @throws NegativeResourceAmountException -> can be throwed by "decreaseAmount" method
     */
    @Test
    void checkRemoveResourceFromStrongboxIfNotStored()
            throws NotEqualResourceTypeException, NotContainedResourceException, NegativeResourceAmountException {
        Strongbox newStrongbox = new Strongbox();

        StorableResource resourceStone = new StorableResource(ResourceType.STONE, 15);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceStone);
        StorableResource resourceShield = new StorableResource(ResourceType.SHIELD, 8);

        newStrongbox.storeResourceInStrongbox(resourceStone);
        try{
            newStrongbox.removeResourceFromStrongbox(resourceShield);
            fail();
        }catch (NotContainedResourceException e){
            assertTrue(resourceArray.equals(newStrongbox.getAllStoredResources()));
        }
    }

}
