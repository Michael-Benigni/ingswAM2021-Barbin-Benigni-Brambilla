package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gameresources.markettray.MarbleColour;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketMarble;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Tests for "MarketAction" class.
 */
public class MarketActionTest {

    private MarketTray marketTray;

    private void initMarketTray() throws NegativeResourceAmountException {
        MarketMarble m = new MarketMarble(MarbleColour.BLUE, new StorableResource(ResourceType.SHIELD, 1));
        HashMap<MarketMarble, Integer> map = new HashMap<>(0);
        map.put(m, 6);
        marketTray = new MarketTray(3, 2, map);
    }

    @Test
    void checkPerformActionIfCorrect() {
    //TODO: how can i check the which marbles are picked for the market tray?
    }
}
