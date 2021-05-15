package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.utils.network.Header;
import java.util.HashMap;

public class ActionFactory {
    private static final HashMap<Header, Class<? extends Action>> map = init ();

    private static HashMap<Header, Class<? extends Action>> init() {
        map.put (Header.BOARD_PRODUCTION, BoardProductionAction.class);
        map.put (Header.BUY_CARD, BuyCardAction.class);
        map.put (Header.DISCARD_ALL_RESOURCES, DiscardAllResources.class);
        map.put (Header.END_PRODUCTION, EndProductionAction.class);
        map.put (Header.END_TURN, EndTurnAction.class);
        map.put (Header.EXTRA_PRODUCTION, ExtraBoardProductionAction.class);
        map.put (Header.LEADER, LeaderAction.class);
        map.put (Header.MARKET, MarketAction.class);
        map.put (Header.PRODUCTION_CARD, ProductionCardAction.class);
        map.put (Header.STRONGBOX, StrongboxAction.class);
        map.put (Header.SWAP_DEPOTS, SwapDepots.class);
        map.put (Header.TEMP_CONTAINER, TempContainerAction.class);
        map.put (Header.TRANSFORM_WHITE_MARBLE, TransformWhiteMarbleAction.class);
        map.put (Header.WAREHOUSE, WarehouseAction.class);
        return map;
    }

    public static Class<? extends Action> getActionType(Header header) {
        HashMap<Header, Class<? extends Action>> map = new HashMap<> ();
        return map.get (header);
    }
}
