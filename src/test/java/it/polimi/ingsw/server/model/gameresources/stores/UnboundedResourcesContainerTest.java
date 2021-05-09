package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.NotContainedResourceException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class UnboundedResourcesContainerTest {

    /**
     * Test on "storeAll" method of this class.
     * It tests if the method correctly updates the array of resources contained in an unbounded resource container.
     * @throws NegativeResourceAmountException can be thrown by constructor method of "StorableResource" class.
     */
    @Test
    void checkStoreAllIfCorrect() throws NegativeResourceAmountException {
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        ArrayList<StorableResource> list1 = new ArrayList<>(0);
        list1.add(new StorableResource(ResourceType.COIN, 9));
        list1.add(new StorableResource(ResourceType.STONE, 22));
        container.storeAll(list1);
        assertEquals(container.getAllResources(), list1);
        ArrayList<StorableResource> list2 = new ArrayList<>(0);
        list2.add(new StorableResource(ResourceType.SHIELD, 4));
        list2.add(new StorableResource(ResourceType.SERVANT, 0));
        container.storeAll(list2);
        list1.addAll(list2);
        assertEquals(container.getAllResources(), list1);
    }


    /**
     * Test on "store" method.
     * Test if the method put a new resource in an empty strongbox.
     * @throws Exception can be throwed by "store" method.
     */
    @Test
    void checkStoreIfAddResourceWhenEmpty() throws Exception {
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        StorableResource resourceCoin = new StorableResource(ResourceType.COIN, 80);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceCoin);
        container.store(resourceCoin);
        assertEquals(container.getAllResources(), resourceArray);
    }


    /**
     * Test on "store" method.
     * Test if the method put a new resource in a strongbox that already contains a resource with different type.
     * @throws Exception can be throwed by "store" method.
     */
    @Test
    void checkStoreIfNotAlreadyContained() throws Exception {
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        StorableResource resourceStone = new StorableResource(ResourceType.STONE, 45);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceStone);
        StorableResource resourceShield = new StorableResource(ResourceType.SHIELD, 18);
        resourceArray.add(resourceShield);
        container.store(resourceStone);
        container.store(resourceShield);
        assertEquals(container.getAllResources(), resourceArray);
    }


    /**
     * Test on "store" method.
     * Test if the method increases the amount of a contained resource correctly.
     * @throws Exception can be throwed by "store" method.
     */
    @Test
    void checkStoreIfAlreadyContained() throws Exception {
        StorableResource resourceStone = new StorableResource(ResourceType.STONE, 9);
        StorableResource resourceCoin1 = new StorableResource(ResourceType.COIN, 5);
        StorableResource resourceCoin2 = new StorableResource(ResourceType.COIN, 13);
        StorableResource resourceTotalCoin = new StorableResource(ResourceType.COIN, 5+13);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceStone);
        resourceArray.add(resourceTotalCoin);
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        container.store(resourceStone);
        container.store(resourceCoin1);
        container.store(resourceCoin2);
        assertEquals(resourceArray, container.getAllResources());
    }


    /**
     * Test on "remove" method
     * Test if the method decrease the amount of the stored resource correctly.
     * @throws Exception can be throwed by "store" method.
     */
    @Test
    void checkRemoveIfCorrect() throws Exception {

        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        StorableResource resourceShield = new StorableResource(ResourceType.SHIELD, 15);
        StorableResource resourceCoin1 = new StorableResource(ResourceType.COIN, 10);
        StorableResource resourceCoin2 = new StorableResource(ResourceType.COIN, 7);
        StorableResource resourceCoin3 = new StorableResource(ResourceType.COIN, 3);
        container.store(resourceShield);
        container.store(resourceCoin1);
        container.remove(resourceCoin2);
        ArrayList<StorableResource> listOfResources = new ArrayList<>(0);
        listOfResources.add(resourceShield);
        listOfResources.add(resourceCoin3);
        assertEquals(listOfResources, container.getAllResources());
    }


    /**
     * Test on "remove" method.
     * Test if the method throws the NotContainedResourceException successfully.
     * @throws Exception can be throwed by "store" method.
     */
    @Test
    void checkRemoveIfNotStored() throws Exception {
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        StorableResource resourceStone = new StorableResource(ResourceType.STONE, 15);
        ArrayList<StorableResource> resourceArray = new ArrayList<>(0);
        resourceArray.add(resourceStone);
        StorableResource resourceShield = new StorableResource(ResourceType.SHIELD, 8);
        container.store(resourceStone);
        try{
            container.remove(resourceShield);
            fail();
        }catch (NotContainedResourceException e){
            assertEquals(container.getAllResources(), resourceArray);
        }
    }

    /**
     * Test on "remove" method of this class.
     * It tests if the method throws successfully an exception when the amount of the provided resource if greater
     * than the amount of the resource in the container.
     * @throws NegativeResourceAmountException can be thrown by constructor method of "StorableResource".
     */
    @Test
    void checkRemoveIfNotEnough() throws NegativeResourceAmountException {
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        StorableResource stone = new StorableResource(ResourceType.STONE, 4);
        StorableResource coin = new StorableResource(ResourceType.COIN, 20);
        container.store(stone);
        container.store(coin);
        StorableResource tooManyStones = new StorableResource(ResourceType.STONE, 100);
        try {
            container.remove(tooManyStones);
            fail();
        } catch (NegativeResourceAmountException e) {
            ArrayList<StorableResource> listOfResources = new ArrayList<>(0);
            listOfResources.add(stone);
            listOfResources.add(coin);
            assertEquals(container.getAllResources(), listOfResources);
        } catch (NotContainedResourceException e) {
            fail();
        }
    }

    /**
     * Test on "remove" method of this class.
     * It tests if the method successfully empties the container.
     * @throws NegativeResourceAmountException can be thrown by constructor method of "StorableResource".
     */
    @Test
    void checkRemoveIfBecomesEmpty() throws NegativeResourceAmountException {
        UnboundedResourcesContainer container = new UnboundedResourcesContainer();
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 89);
        try {
            container.store(servant);
            container.remove(servant);
            ArrayList<StorableResource> emptyList = new ArrayList<>(0);
            assertEquals(container.getAllResources(), emptyList);
        } catch(Exception e) {
            fail();
        }
    }
}
