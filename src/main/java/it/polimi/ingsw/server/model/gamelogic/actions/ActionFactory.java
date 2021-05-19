package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.utils.network.ToServer;

import java.util.HashMap;

public class ActionFactory {
    private static final HashMap<ToServer, Class<? extends Action>> map = init ();

    private static HashMap<ToServer, Class<? extends Action>> init() {
        map.put (ToServer.BOARD_PRODUCTION, BoardProductionAction.class);
        map.put (ToServer.BUY_CARD, BuyCardAction.class);
        map.put (ToServer.DISCARD_ALL_RESOURCES, DiscardAllResources.class);
        map.put (ToServer.END_PRODUCTION, EndProductionAction.class);
        map.put (ToServer.END_TURN, EndTurnAction.class);
        map.put (ToServer.EXTRA_PRODUCTION, ExtraBoardProductionAction.class);
        map.put (ToServer.LEADER, LeaderAction.class);
        map.put (ToServer.MARKET, MarketAction.class);
        map.put (ToServer.PRODUCTION_CARD, ProductionCardAction.class);
        map.put (ToServer.STRONGBOX, StrongboxAction.class);
        map.put (ToServer.SWAP_DEPOTS, SwapDepots.class);
        map.put (ToServer.TEMP_CONTAINER, TempContainerAction.class);
        map.put (ToServer.TRANSFORM_WHITE_MARBLE, TransformWhiteMarbleAction.class);
        map.put (ToServer.WAREHOUSE, WarehouseAction.class);
        return map;
    }

    public static Class<? extends Action> getActionType(ToServer toServer) {
        HashMap<ToServer, Class<? extends Action>> map = new HashMap<> ();
        return map.get (toServer);
    }
}
