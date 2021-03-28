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
     * Test if the created object is accordant to the provided parameters, so it tests also "getAmount()" and
     * "getResourceType()"
     */
    @Test
    void checkConstructorIfCorrect() throws NegativeResourceAmountException {
        int amount = 5;
        StorableResource resource = new StorableResource(ResourceType.COIN, amount);
        assertEquals(resource.getAmount(),amount);
        assertSame(resource.getResourceType(),ResourceType.COIN);
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
     * Test on the "increaseAmount()" method
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

        assertEquals(storedResource.getAmount(),(amountStoredResource + amountNewResource));

    }


    /**
     * Test on the "increaseAmount()" method
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
            assertEquals(storedResource.getAmount(),amountStoredResource);
        }

    }


    /**
     * Test on the "decreaseAmount()" method
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

        assertEquals(storedResource.getAmount(),(amountStoredResource - amountNewResource));

    }


    /**
     * Test on the "decreaseAmount()" method
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
            assertEquals(storedResource.getAmount(),amountStoredResource);
        }

    }


    /**
     * Test on the "decreaseAmount()" method
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
            assertEquals(storedResource.getAmount(), amountStoredResource);
        } catch (NotEqualResourceTypeException e){
            fail();
        }

    }

}