package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StrongboxActionTest extends ActionTest {

    @Test
    void performTest() throws NegativeResourceAmountException, NotContainedResourceException {
        StorableResource stone = new StorableResource(ResourceType.STONE, 2);
        StorableResource coin = new StorableResource(ResourceType.COIN, 3);
        StrongboxAction strongboxAction1 = new StrongboxAction("store", stone);
        strongboxAction1.perform(game, player1);
        ArrayList<StorableResource> resourcesFromStrongbox = player1.getPersonalBoard().getStrongbox().getAllResources();
        assertTrue(stone.equals(resourcesFromStrongbox.get(0)));

        StrongboxAction strongboxAction2 = new StrongboxAction("store", coin);
        ArrayList<StorableResource> listOfAddedResources = new ArrayList<>();
        listOfAddedResources.add(stone);
        listOfAddedResources.add(coin);
        strongboxAction2.perform(game, player1);
        resourcesFromStrongbox = player1.getPersonalBoard().getStrongbox().getAllResources();
        assertTrue(resourcesFromStrongbox.equals(listOfAddedResources));

        StrongboxAction strongboxAction3 = new StrongboxAction("remove", stone);
        strongboxAction3.perform(game, player1);
        resourcesFromStrongbox = player1.getPersonalBoard().getStrongbox().getAllResources();
        listOfAddedResources.remove(stone);
        assertTrue(resourcesFromStrongbox.equals(listOfAddedResources));

        try{
            strongboxAction3.perform(game, player1);
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getUndoActionTest() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.SERVANT, 1);
        StrongboxAction strongboxAction1 = new StrongboxAction("store", resource);
        StrongboxAction strongboxAction2 = new StrongboxAction("remove", resource);
        Action action1 = strongboxAction1.getUndoAction();
        Action action2 = strongboxAction2.getUndoAction();
        assertTrue(strongboxAction2.equals(action1));
        assertTrue(strongboxAction1.equals(action2));
    }

    @Test
    void getResourceTest() throws NegativeResourceAmountException {
        StorableResource resource = new StorableResource(ResourceType.SERVANT, 1);
        StrongboxAction strongboxAction = new StrongboxAction("store", resource);
        StorableResource resource1 = strongboxAction.getResource();
        assertTrue(resource.equals(resource1));
    }
}