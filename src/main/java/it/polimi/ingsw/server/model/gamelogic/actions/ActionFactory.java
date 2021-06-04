package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.utils.network.Header;
import java.util.HashMap;

public class ActionFactory {
    private static final HashMap<Header.ToServer, Class<? extends Action>> map = init ();

    private static HashMap<Header.ToServer, Class<? extends Action>> init() {
        HashMap<Header.ToServer, Class<? extends Action>> map = new HashMap<> ();
        map.put (Header.ToServer.BOARD_PRODUCTION, BoardProductionAction.class);
        map.put (Header.ToServer.BUY_CARD, BuyCardAction.class);
        map.put (Header.ToServer.DISCARD_ALL_RESOURCES, DiscardAllResources.class);
        map.put (Header.ToServer.END_PRODUCTION, EndProductionAction.class);
        map.put (Header.ToServer.END_TURN, EndTurnAction.class);
        map.put (Header.ToServer.EXTRA_PRODUCTION, ExtraBoardProductionAction.class);
        map.put (Header.ToServer.LEADER, LeaderAction.class);
        map.put (Header.ToServer.MARKET, MarketAction.class);
        map.put (Header.ToServer.PRODUCTION_CARD, ProductionCardAction.class);
        map.put (Header.ToServer.STRONGBOX, StrongboxAction.class);
        map.put (Header.ToServer.SWAP_DEPOTS, SwapDepots.class);
        map.put (Header.ToServer.TEMP_CONTAINER, TempContainerAction.class);
        map.put (Header.ToServer.TRANSFORM_WHITE_MARBLE, TransformWhiteMarbleAction.class);
        map.put (Header.ToServer.WAREHOUSE, WarehouseAction.class);
        return map;
    }

    public static Class<? extends Action> getActionType(Header.ToServer toServer) {
        return map.get (toServer);
    }
}
