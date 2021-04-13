package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarketMarbleTest {

    @Test
    void getCorrespondentResourceEmptyResource()  {
        Resource resource = new EmptyResource();
        MarketMarble marble = new MarketMarble(MarbleColour.BLUE, resource);
        Resource resourceToTest = marble.getCorrespondentResource();
        assertTrue(resourceToTest instanceof EmptyResource);
        assertEquals(resourceToTest, resource);
    }

    @Test
    void getCorrespondentResourceFaithPoint() throws NegativeResourceAmountException {
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