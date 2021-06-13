package it.polimi.ingsw.server.model.gameresources.markettray;

import it.polimi.ingsw.server.model.exception.InvalidMarketColumnException;
import it.polimi.ingsw.server.model.exception.InvalidMarketRowException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gameresources.Resource;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.server.model.gameresources.stores.EmptyResource;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MarketTrayTest {

    private int row = 3;
    private int col = 4;

    /**
     * Method used to create a new market tray.
     * @return a new market tray.
     * @throws NegativeResourceAmountException
     */
    public static MarketTray initMarketTray() throws NegativeResourceAmountException {
        HashMap<MarketMarble, Integer> map = initMarbles();
        MarketTray marketTray = new MarketTray(4, 3, map);
        return marketTray;
    }

    /**
     * Method used to create an hashmap useful to construct a market tray.
     * @return an hashmap with a MarketMarble as key and an Integer as value.
     * @throws NegativeResourceAmountException
     */
    private static HashMap<MarketMarble, Integer> initMarbles() throws NegativeResourceAmountException {
        HashMap<MarketMarble, Integer> marbles = new HashMap<>(0);
        MarketMarble m1 = new MarketMarble(MarbleColour.BLUE, new StorableResource(ResourceType.SHIELD, 1));
        MarketMarble m2 = new MarketMarble(MarbleColour.YELLOW, new StorableResource(ResourceType.COIN, 1));
        MarketMarble m3 = new MarketMarble(MarbleColour.GREY, new StorableResource(ResourceType.STONE, 1));
        MarketMarble m4 = new MarketMarble(MarbleColour.PURPLE, new StorableResource(ResourceType.SERVANT, 1));
        MarketMarble m5 = new MarketMarble(MarbleColour.RED, new FaithPoint( 1));
        MarketMarble m6 = new MarketMarble(MarbleColour.WHITE, new EmptyResource());
        marbles.put(m1, 2);
        marbles.put(m2, 2);
        marbles.put(m3, 2);
        marbles.put(m4, 2);
        marbles.put(m5, 1);
        marbles.put(m6, 4);
        return marbles;
    }

    @Test
    void pickResourcesOnRow() throws InvalidMarketRowException, NegativeResourceAmountException {
        MarketTray marketTray = initMarketTray();
        List<Resource> resourceList = marketTray.pickResourcesOnRow(1);
        assertInstanceOf(ArrayList.class, resourceList);
        assertNotNull(resourceList);
        assert (resourceList.size() == col);
        for (Resource resource: resourceList) {
            assertInstanceOf(Resource.class, resource);
        }
    }

    @Test
    void pickResourcesOnColumn() throws InvalidMarketColumnException, NegativeResourceAmountException {
        MarketTray marketTray = initMarketTray();
        List<Resource> resourceList = marketTray.pickResourcesOnColumn(1);
        assertInstanceOf(ArrayList.class, resourceList);
        assertNotNull(resourceList);
        assert (resourceList.size() == row);
        for (Resource resource: resourceList) {
            assertInstanceOf(Resource.class, resource);
        }
    }
}