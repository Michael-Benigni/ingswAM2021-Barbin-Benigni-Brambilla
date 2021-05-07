package it.polimi.ingsw.server.model.gameresources.markettray;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gameresources.Resource;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarketMarbleTest {

    @Test
    void getCorrespondentResourceEmptyResource() throws NegativeResourceAmountException {
        Resource resource = new StorableResource(ResourceType.COIN, 1);
        MarketMarble marble = new MarketMarble(MarbleColour.BLUE, resource);
        Resource resourceToTest = marble.getCorrespondentResource();
        assertTrue(resourceToTest instanceof StorableResource);
        assertEquals(resourceToTest, resource);
    }

    @Test
    void getCorrespondentResourceFaithPoint() {
        Resource resource = new FaithPoint(1);
        MarketMarble marble = new MarketMarble(MarbleColour.BLUE, resource);
        Resource resourceToTest = marble.getCorrespondentResource();
        assertTrue(resourceToTest instanceof FaithPoint);
        assertEquals(resourceToTest, resource);
    }

    @Test
    void getCorrespondentResourceStorableResource() throws NegativeResourceAmountException {
        Resource resource = new StorableResource(ResourceType.COIN, 1);
        MarketMarble marble = new MarketMarble(MarbleColour.BLUE, resource);
        Resource resourceToTest = marble.getCorrespondentResource();
        assertTrue(resourceToTest instanceof StorableResource);
        assertEquals(resourceToTest, resource);
    }
}