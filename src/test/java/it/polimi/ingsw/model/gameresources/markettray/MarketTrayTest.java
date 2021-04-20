package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.InvalidMarketColumnException;
import it.polimi.ingsw.exception.InvalidMarketRowException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MarketTrayTest {

    HashMap<MarketMarble, Integer> marbles() throws NegativeResourceAmountException {
        MarketMarble m1 = new MarketMarble(MarbleColour.BLUE, new StorableResource(ResourceType.SHIELD, 1));
        MarketMarble m2 = new MarketMarble(MarbleColour.YELLOW, new StorableResource(ResourceType.COIN, 1));
        MarketMarble m3 = new MarketMarble(MarbleColour.GREY, new StorableResource(ResourceType.STONE, 1));
        MarketMarble m4 = new MarketMarble(MarbleColour.PURPLE, new StorableResource(ResourceType.SERVANT, 1));
        MarketMarble m5 = new MarketMarble(MarbleColour.RED, new FaithPoint( 1));
        MarketMarble m6 = new MarketMarble(MarbleColour.WHITE, new EmptyResource());
        HashMap<MarketMarble, Integer> map = new HashMap<>();
        map.put(m1, 2);
        map.put(m2, 2);
        map.put(m3, 2);
        map.put(m4, 2);
        map.put(m5, 1);
        map.put(m6, 4);
        return map;
    }

    @Test
    void pickResourcesOnRow() throws NegativeResourceAmountException, InvalidMarketRowException {
        int col = 4;
        int row = 3;
        MarketTray marketTray = new MarketTray(col, row, marbles());
        List<Resource> resourceList = null;
        resourceList = marketTray.pickResourcesOnRow(1);
        assertInstanceOf(ArrayList.class, resourceList);
        assertNotNull(resourceList);
        assert (resourceList.size() == row);
        for (Resource resource: resourceList) {
            assertInstanceOf(Resource.class, resource);
        }
    }

    @Test
    void pickResourcesOnColumn() throws NegativeResourceAmountException, InvalidMarketColumnException {
        int col = 4;
        int row = 3;
        MarketTray marketTray = new MarketTray(col, row, marbles());
        List<Resource> resourceList = null;
        resourceList = marketTray.pickResourcesOnColumn(1);
        assertInstanceOf(ArrayList.class, resourceList);
        assertNotNull(resourceList);
        assert (resourceList.size() == row);
        for (Resource resource: resourceList) {
            assertInstanceOf(Resource.class, resource);
        }
    }
}