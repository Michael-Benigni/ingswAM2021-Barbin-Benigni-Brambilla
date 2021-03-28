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

        int amountStockResource = 5;
        StorableResource stockResource = new StorableResource(ResourceType.SHIELD, amountStockResource);


        int amountNewResource = 3;
        StorableResource newResource = new StorableResource(ResourceType.STONE, amountNewResource);


        try{
            stockResource.increaseAmount(newResource);
            fail();
        } catch (NotEqualResourceTypeException e){
            assertEquals(stockResource.getAmount(),amountStockResource);
        }

    }


    /**
     * Test on the "decreaseAmount()" method
     * Test if the method returns exactly the difference between the 2 amounts of resources
     */
    @Test
    void checkDecreaseAmountIfCorrect() throws NegativeResourceAmountException {

        int amountStockResource = 15;
        StorableResource stockResource = new StorableResource(ResourceType.COIN, amountStockResource);


        int amountNewResource = 9;
        StorableResource newResource = new StorableResource(ResourceType.COIN, amountNewResource);


        try{
            stockResource.decreaseAmount(newResource);
        } catch (NegativeResourceAmountException | NotEqualResourceTypeException e) {
            fail();
        }

        assertEquals(stockResource.getAmount(),(amountStockResource - amountNewResource));

    }


    /**
     * Test on the "decreaseAmount()" method
     * Test if the method throws the NotEqualResourceTypeException successfully
     */
    @Test
    void checkDecreaseAmountIfDifferentResourceType() throws NegativeResourceAmountException {

        int amountStockResource = 7;
        StorableResource stockResource = new StorableResource(ResourceType.SHIELD, amountStockResource);


        int amountNewResource = 2;
        StorableResource newResource = new StorableResource(ResourceType.SERVANT, amountNewResource);


        try{
            stockResource.decreaseAmount(newResource);
            fail();
        } catch (NegativeResourceAmountException e) {
            fail();
        } catch (NotEqualResourceTypeException e){
            assertEquals(stockResource.getAmount(),amountStockResource);
        }

    }


    /**
     * Test on the "decreaseAmount()" method
     * Test if the method throws the NegativeResourceAmountException successfully
     */
    @Test
    void checkDecreaseAmountIfNegativeResourceAmount() throws NegativeResourceAmountException {

        int amountStockResource = 10;
        StorableResource stockResource = new StorableResource(ResourceType.COIN, amountStockResource);


        int amountNewResource = 19;
        StorableResource newResource = new StorableResource(ResourceType.COIN, amountNewResource);


        try{
            stockResource.decreaseAmount(newResource);
            fail();
        } catch (NegativeResourceAmountException e) {
            assertEquals(stockResource.getAmount(), amountStockResource);
        } catch (NotEqualResourceTypeException e){
            fail();
        }

    }

}