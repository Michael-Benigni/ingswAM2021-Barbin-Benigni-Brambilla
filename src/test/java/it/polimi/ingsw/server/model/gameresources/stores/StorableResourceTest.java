package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on StorableResource class
 */
public class StorableResourceTest {

    /**
     * Test on the constructor method of this class.
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
     * Test on "increaseAmount" method of this class.
     * It tests if the method returns exactly the sum of the 2 amounts of resources
     */
    @Test
    void checkIncreaseAmountIfCorrect() throws NegativeResourceAmountException {
        StorableResource storedResource = new StorableResource(ResourceType.STONE, 3);
        StorableResource resourceToBeAdded = new StorableResource(ResourceType.STONE, 5);
        try{
            storedResource = storedResource.increaseAmount(resourceToBeAdded);
            assertEquals(storedResource, new StorableResource(ResourceType.STONE, 8));
        } catch (NotEqualResourceTypeException e) {
            fail();
        }
    }

    /**
     * Test on "increaseAmount" method of this class.
     * It Tests if the method throws the NotEqualResourceTypeException successfully
     */
    @Test
    void checkIncreaseAmountIfDifferentResourceType() throws NegativeResourceAmountException {
        StorableResource storedResource = new StorableResource(ResourceType.SHIELD, 5);
        StorableResource resourceToBeAdded = new StorableResource(ResourceType.STONE, 3);
        try{
            storedResource.increaseAmount(resourceToBeAdded);
            fail();
        } catch (NotEqualResourceTypeException e){
            assertEquals(storedResource, new StorableResource(ResourceType.SHIELD, 5));
        }
    }

    /**
     * Test on "decreaseAmount" method
     * Test if the method returns exactly the difference between the 2 amounts of resources
     */
    @Test
    void checkDecreaseAmountIfCorrect() throws Exception {
        StorableResource storedResource = new StorableResource(ResourceType.COIN, 15);
        StorableResource resourceToBeSubtracted = new StorableResource(ResourceType.COIN, 9);
        try{
            storedResource = storedResource.decreaseAmount(resourceToBeSubtracted);
            assertEquals(storedResource, new StorableResource(ResourceType.COIN, 6));
        } catch (NegativeResourceAmountException | NotEqualResourceTypeException e) {
            fail();
        }
    }

    /**
     * Test on "decreaseAmount" method
     * Test if the method throws the NotEqualResourceTypeException successfully.
     */
    @Test
    void checkDecreaseAmountIfDifferentResourceType() throws Exception {
        StorableResource storedResource = new StorableResource(ResourceType.SHIELD, 7);
        StorableResource resourceToBeSubtracted = new StorableResource(ResourceType.SERVANT, 2);
        try{
            storedResource = storedResource.decreaseAmount(resourceToBeSubtracted);
            fail();
        } catch (NegativeResourceAmountException e) {
            fail();
        } catch (NotEqualResourceTypeException e){
            assertEquals(storedResource, new StorableResource(ResourceType.SHIELD, 7));
        }
    }


    /**
     * Test on "decreaseAmount" method
     * Test if the method throws the NegativeResourceAmountException successfully
     */
    @Test
    void checkDecreaseAmountIfNegativeResourceAmount() throws Exception {
        StorableResource storedResource = new StorableResource(ResourceType.COIN, 10);
        StorableResource resourceToBeSubtracted = new StorableResource(ResourceType.COIN, 19);
        try{
            storedResource = storedResource.decreaseAmount(resourceToBeSubtracted);
            fail();
        } catch (NegativeResourceAmountException e) {
            assertEquals(storedResource, new StorableResource(ResourceType.COIN, 10));
        } catch (NotEqualResourceTypeException e){
            fail();
        }

    }

    /**
     * Test on "decreaseAmount" method of this class.
     * It tests if the method throws successfully an exception when the resultant amount is equal to zero.
     */
    @Test
    void checkDecreaseAmountWhenSameAmount() throws NotEqualResourceTypeException, NegativeResourceAmountException {
        StorableResource storedResource = new StorableResource(ResourceType.SERVANT, 5);
        try {
            storedResource.decreaseAmount(storedResource);
        } catch(NullResourceAmountException e) {
            assertEquals(storedResource, new StorableResource(ResourceType.SERVANT, 5));
        }
    }

    /**
     * Test on "ifSameResourceType" method
     * Test if the method returns the right answer
     */
    @Test
    void checkIfSameResourceTypeIfCorrect() throws NegativeResourceAmountException {
        StorableResource resourceCoin = new StorableResource(ResourceType.COIN, 5);
        StorableResource resourceStone = new StorableResource(ResourceType.STONE, 6);
        StorableResource resourceStone2 = new StorableResource(ResourceType.STONE, 8);
        assertTrue(resourceStone.ifSameResourceType(resourceStone2));
        assertFalse(resourceStone.ifSameResourceType(resourceCoin));
        assertFalse(resourceCoin.ifSameResourceType(resourceStone2));
    }

    /**
     * Test on "equals" method.
     * Test if the method works correctly.
     * @throws NegativeResourceAmountException can be throwed by the constructor method of "StorableResource" class
     */
    @Test
    void checkEqualsIfCorrect() throws NegativeResourceAmountException {
        StorableResource resourceStone1 = new StorableResource(ResourceType.STONE, 16);
        StorableResource resourceStone2 = new StorableResource(ResourceType.STONE, 16);
        StorableResource resourceServant = new StorableResource(ResourceType.SERVANT, 24);
        assertEquals(resourceStone2, resourceStone1);
        assertNotEquals(resourceServant, resourceStone2);
        assertNotEquals(resourceStone1, resourceServant);
        assertNotSame(resourceStone1, resourceStone2);
    }

    /**
     * Test on "amountLessEqualThan" method of this class.
     * It tests if the method returns the right answer.
     */
    @Test
    void checkAmountLessEqualThanIfCorrect() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.SHIELD, 8);
        assertTrue(resource.amountLessEqualThan(19));
        assertFalse(resource.amountLessEqualThan(2));
    }

    /**
     * Test on "setAmount" method of this class.
     * It tests if the method correctly set the amount of this resource to the provided int.
     */
    @Test
    void checkSetAmountIfCorrect() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.STONE, 194);
        resource.setAmount(3);
        assertEquals(resource, new StorableResource(ResourceType.STONE, 3));
    }

    /**
     * Test on "setAmount" method of this class.
     * It tests if the method doesn't modify the storable resource when the provided amount is negative.
     */
    @Test
    void checkSetAmountIfNegative() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.SERVANT, 21);
        resource.setAmount(-12);
        assertEquals(resource, new StorableResource(ResourceType.SERVANT, 21));
    }

    /**
     * Test on "containedIn" method of this class.
     * It tests if the method returns the right answer.
     */
    @Test
    void checkContainedInIfCorrect() throws NegativeResourceAmountException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException, WrongDepotIndexException, SameResourceTypeInDifferentDepotsException {
        StorableResource resourceRequired = new StorableResource(ResourceType.STONE, 4);
        Player player = new Player();
        WarehouseDepots warehouse = new WarehouseDepots(3, new ArrayList<>(Arrays.asList(3, 4, 5)));
        PersonalBoard pb = new PersonalBoard(warehouse, 1, 3, 2, 2);
        player.buildBoard(pb);
        player.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.STONE, 3));
        warehouse.store(new StorableResource(ResourceType.STONE, 3), 0);
        warehouse.store(new StorableResource(ResourceType.SHIELD, 5), 2);
        player.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.COIN, 5));
        player.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.SERVANT, 18));
        assertTrue(resourceRequired.containedIn(player));
        StorableResource resourceNotContained = new StorableResource(ResourceType.SHIELD, 108);
        assertFalse(resourceNotContained.containedIn(player));
    }
}