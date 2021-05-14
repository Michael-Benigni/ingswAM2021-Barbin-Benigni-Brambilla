package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.UnboundedResourcesContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PayActionTest extends ActionTest {
    private UnboundedResourcesContainer cost = new UnboundedResourcesContainer();
    private StorableResource coin3 = new StorableResource(ResourceType.COIN, 3);
    private StorableResource stone2 = new StorableResource(ResourceType.STONE, 2);
    private StorableResource servant2 = new StorableResource(ResourceType.SERVANT, 2);
    private StorableResource servant1 = new StorableResource(ResourceType.SERVANT, 1);
    private StorableResource shield3 = new StorableResource(ResourceType.SHIELD, 3);
    private StrongboxAction strongboxActionRemove3coin = new StrongboxAction("remove", coin3);
    private StrongboxAction strongboxActionRemove2stone = new StrongboxAction("remove", stone2);
    private StrongboxAction strongboxActionRemove2servant = new StrongboxAction("remove", servant2);
    private StrongboxAction strongboxActionStore3shield = new StrongboxAction("store", shield3);
    private WarehouseAction warehouseActionRemove3coin = new WarehouseAction("remove", coin3, 2);
    private WarehouseAction warehouseActionRemove2stone = new WarehouseAction("remove", stone2, 1);
    private WarehouseAction warehouseActionRemove2servant = new WarehouseAction("remove", servant2, 0);
    private WarehouseAction warehouseActionStore3shield = new WarehouseAction("store", shield3, 2);
    private ArrayList<StorableResource> allResources = new ArrayList<>();

    PayActionTest() throws NegativeResourceAmountException, NegativeResourceAmountException {
    }

    @BeforeEach
    void initialize() throws NegativeResourceAmountException, WrongDepotIndexException, SameResourceTypeInDifferentDepotsException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        cost.store(shield3);
        cost.store(coin3);
        cost.store(stone2);
        cost.store(servant2);
        player1.getPersonalBoard().getStrongbox().store(coin3);
        player1.getPersonalBoard().getStrongbox().store(stone2);
        player1.getPersonalBoard().getStrongbox().store(servant1);
        player1.getPersonalBoard().getWarehouseDepots().store(coin3, 2);
        player1.getPersonalBoard().getWarehouseDepots().store(stone2, 1);
        player1.getPersonalBoard().getWarehouseDepots().store(servant1, 0);
    }

    @Test
    void payOrUndoWarehouseWithStore() throws Exception {
        player1.getPersonalBoard().getWarehouseDepots().remove(coin3, 2);
        warehouseActionStore3shield.payOrUndo(game, player1, cost);
        warehouseActionRemove2stone.payOrUndo(game, player1, cost);
        warehouseActionRemove2servant.payOrUndo(game, player1, cost);
        assertTrue(cost.getAllResources().size() == 2);
        assertTrue(cost.getAllResources().get(0).equals(coin3));
        assertTrue(cost.getAllResources().get(1).equals(servant2));
        allResources = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertTrue(allResources.size() == 2);
        assertTrue(stone2.equals(allResources.get(1)));
        assertTrue(servant1.equals(allResources.get(0)));

    }

    @Test
    void payOrUndoWarehouseOnlyRemove() throws Exception {
        warehouseActionRemove3coin.payOrUndo(game, player1, cost);
        warehouseActionRemove2stone.payOrUndo(game, player1, cost);
        warehouseActionRemove2servant.payOrUndo(game, player1, cost);
        assertTrue(cost.getAllResources().size() == 2);
        assertTrue(cost.getAllResources().get(0).equals(shield3));
        assertTrue(cost.getAllResources().get(1).equals(servant2));
        allResources = player1.getPersonalBoard().getWarehouseDepots().getAllResources();
        assertTrue(coin3.equals(allResources.get(2)));
        assertTrue(stone2.equals(allResources.get(1)));
        assertTrue(servant1.equals(allResources.get(0)));
    }

    @Test
    void payOrUndoStrongboxOnlyRemove() throws Exception {
        strongboxActionRemove3coin.payOrUndo(game, player1, cost);
        strongboxActionRemove2stone.payOrUndo(game, player1, cost);
        strongboxActionRemove2servant.payOrUndo(game, player1, cost);
        assertTrue(cost.getAllResources().size() == 2);
        assertTrue(cost.getAllResources().get(0).equals(shield3));
        assertTrue(cost.getAllResources().get(1).equals(servant2));
        allResources = player1.getPersonalBoard().getStrongbox().getAllResources();
        assertTrue(stone2.equals(allResources.get(2)));
        assertTrue(coin3.equals(allResources.get(1)));
        assertTrue(servant1.equals(allResources.get(0)));
    }

    @Test
    void payOrUndoStrongboxWithStore() throws Exception {
        strongboxActionStore3shield.payOrUndo(game, player1, cost);
        strongboxActionRemove3coin.payOrUndo(game, player1, cost);
        strongboxActionRemove2stone.payOrUndo(game, player1, cost);
        strongboxActionRemove2servant.payOrUndo(game, player1, cost);
        assertTrue(cost.getAllResources().size() == 1);
        assertTrue(cost.getAllResources().get(0).equals(servant2));
        allResources = player1.getPersonalBoard().getStrongbox().getAllResources();
        assertTrue(stone2.equals(allResources.get(2)));
        assertTrue(coin3.equals(allResources.get(1)));
        assertTrue(servant1.equals(allResources.get(0)));
        assertTrue(allResources.size() == 3);
    }
}