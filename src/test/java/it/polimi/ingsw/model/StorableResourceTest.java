package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on StorableResource class
 */
public class StorableResourceTest {

    /**
     * Test on the constructor method
     * Test if the created object is accordant to the provided parameters.
     */
    @Test
    void checkConstructorIfCorrect() throws NegativeResourceAmountException {
        int amount = 5;
        StorableResource resource = new StorableResource(ResourceType.COIN, amount);
        assertTrue(resource.amountEqualTo(amount));
        assertTrue(resource.resourceTypeEqualTo(ResourceType.COIN));
    }


    /**
     * Test on the constructor method
     * Test if the method throws the NegativeResourceAmountException successfully
     */
    @Test
    void checkConstructorIfNegativeAmount()
    {
        int amount = -104;
        try {
            StorableResource resource = new StorableResource(ResourceType.STONE, amount);
            fail();
        } catch (NegativeResourceAmountException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "increaseAmount()" method
     * Test if the method returns exactly the sum of the 2 amounts of resources
     */
    @Test
    void checkIncreaseAmountIfCorrect() throws NegativeResourceAmountException {

        int amountStoredResource = 3;
        StorableResource storedResource = new StorableResource(ResourceType.STONE, amountStoredResource);


        int amountNewResource = 5;
        StorableResource newResource = new StorableResource(ResourceType.STONE, amountNewResource);


        try{
            storedResource.increaseAmount(newResource);
        } catch (NotEqualResourceTypeException e) {
            fail();
        }

        assertTrue(storedResource.amountEqualTo(amountStoredResource + amountNewResource));

    }


    /**
     * Test on "increaseAmount()" method
     * Test if the method throws the NotEqualResourceTypeException successfully
     */
    @Test
    void checkIncreaseAmountIfDifferentResourceType() throws NegativeResourceAmountException {

        int amountStoredResource = 5;
        StorableResource storedResource = new StorableResource(ResourceType.SHIELD, amountStoredResource);


        int amountNewResource = 3;
        StorableResource newResource = new StorableResource(ResourceType.STONE, amountNewResource);


        try{
            storedResource.increaseAmount(newResource);
            fail();
        } catch (NotEqualResourceTypeException e){
            assertTrue(storedResource.amountEqualTo(amountStoredResource));
        }

    }


    /**
     * Test on "decreaseAmount()" method
     * Test if the method returns exactly the difference between the 2 amounts of resources
     */
    @Test
    void checkDecreaseAmountIfCorrect() throws NegativeResourceAmountException {

        int amountStoredResource = 15;
        StorableResource storedResource = new StorableResource(ResourceType.COIN, amountStoredResource);


        int amountNewResource = 9;
        StorableResource newResource = new StorableResource(ResourceType.COIN, amountNewResource);


        try{
            storedResource.decreaseAmount(newResource);
        } catch (NegativeResourceAmountException | NotEqualResourceTypeException e) {
            fail();
        }

        assertTrue(storedResource.amountEqualTo(amountStoredResource - amountNewResource));

    }


    /**
     * Test on "decreaseAmount()" method
     * Test if the method throws the NotEqualResourceTypeException successfully
     */
    @Test
    void checkDecreaseAmountIfDifferentResourceType() throws NegativeResourceAmountException {

        int amountStoredResource = 7;
        StorableResource storedResource = new StorableResource(ResourceType.SHIELD, amountStoredResource);


        int amountNewResource = 2;
        StorableResource newResource = new StorableResource(ResourceType.SERVANT, amountNewResource);


        try{
            storedResource.decreaseAmount(newResource);
            fail();
        } catch (NegativeResourceAmountException e) {
            fail();
        } catch (NotEqualResourceTypeException e){
            assertTrue(storedResource.amountEqualTo(amountStoredResource));
        }

    }


    /**
     * Test on "decreaseAmount()" method
     * Test if the method throws the NegativeResourceAmountException successfully
     */
    @Test
    void checkDecreaseAmountIfNegativeResourceAmount() throws NegativeResourceAmountException {

        int amountStoredResource = 10;
        StorableResource storedResource = new StorableResource(ResourceType.COIN, amountStoredResource);


        int amountNewResource = 19;
        StorableResource newResource = new StorableResource(ResourceType.COIN, amountNewResource);


        try{
            storedResource.decreaseAmount(newResource);
            fail();
        } catch (NegativeResourceAmountException e) {
            assertTrue(storedResource.amountEqualTo(amountStoredResource));
        } catch (NotEqualResourceTypeException e){
            fail();
        }

    }


    /**
     * Test on "ifSameResourceType()" method
     * Test if the method returns the right answer
     */
    @Test
    void checkIfSameResourceTypeIfCorrect() throws NegativeResourceAmountException {

        int amountOfCoin = 5;
        StorableResource resourceCoin = new StorableResource(ResourceType.COIN, amountOfCoin);

        int amountOfStone = 6;
        StorableResource resourceStone = new StorableResource(ResourceType.STONE, amountOfStone);

        int amountOfStone2 = 8;
        StorableResource resourceStone2 = new StorableResource(ResourceType.STONE, amountOfStone2);

        assertTrue(resourceStone.ifSameResourceType(resourceStone2));
        assertFalse(resourceStone.ifSameResourceType(resourceCoin));
        assertFalse(resourceCoin.ifSameResourceType(resourceStone2));
    }


    /**
     * Test on "equals" method.
     * Test if the method works correctly.
     * @throws NegativeResourceAmountException -> can be throwed by the constructor method of "StorableResource" class
     */
    @Test
    void checkEqualsIfCorrect() throws NegativeResourceAmountException {

        int amountOfStone = 16;
        StorableResource resourceStone1 = new StorableResource(ResourceType.STONE, amountOfStone);
        StorableResource resourceStone2 = new StorableResource(ResourceType.STONE, amountOfStone);

        int amountOfServant = 24;
        StorableResource resourceServant = new StorableResource(ResourceType.SERVANT, amountOfServant);

        assertTrue(resourceStone1.equals(resourceStone2));
        assertFalse(resourceStone2.equals(resourceServant));
        assertFalse(resourceServant.equals(resourceStone1));
        assertNotSame(resourceStone1, resourceStone2);

    }

}