package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.utils.network.Header;
import java.util.HashMap;

public class UpdateFactory {
    private static HashMap<Header.ToClient, Class<? extends ViewUpdate>> map = init ();

    private static HashMap<Header.ToClient, Class<? extends ViewUpdate>> init() {
        HashMap<Header.ToClient, Class<? extends ViewUpdate>> map = new HashMap<> ();
        map.put (Header.ToClient.USER_REGISTERED, UserRegisteredUP.class);
        map.put (Header.ToClient.YOUR_TURN, YourTurnUP.class);
        map.put (Header.ToClient.TURN_POSITION_UPDATE, TurnPositionUP.class);
        map.put (Header.ToClient.FULL_ROOM, WaitingRoomFullUP.class);
        map.put (Header.ToClient.LEADER_CARDS_UPDATE, SlotLeaderCardUpdate.class);
        map.put (Header.ToClient.MARKET_UP, MarketUP.class);
        map.put (Header.ToClient.REMOVE_SHOW_GRID, RemoveShowGridUpdate.class);
        map.put (Header.ToClient.SHOW_INITIAL_GRID, CardsGridInitialUpdate.class);
        map.put (Header.ToClient.WAREHOUSE_UPDATE, WarehouseUpdate.class);
        map.put (Header.ToClient.SLOT_DEVCARD_UPDATE, SlotDevCardUpdate.class);
        map.put (Header.ToClient.GET_PENALTY_UPDATE, GetPenaltyUpdate.class);
        map.put (Header.ToClient.STRONGBOX_UPDATE, StrongboxUpdate.class);
        map.put (Header.ToClient.TEMP_CONTAINER_UPDATE, TempContainerUpdate.class);
        map.put (Header.ToClient.INITIAL_FAITH_TRACK_UPDATE, InitialFaithTrackUpdate.class);
        map.put (Header.ToClient.PLAYER_POSITION_UP, PlayerPositionUP.class);
        map.put (Header.ToClient.WAIT_YOUR_TURN, WaitYourTurnUP.class);
        map.put (Header.ToClient.LAST_ROUND_UP, LastRoundUP.class);
        map.put (Header.ToClient.SET_NUM_PLAYERS, SetNumPlayersResponseUP.class);
        map.put (Header.ToClient.FAITH_TRACK_UPDATE, FaithTrackOneStepUpdate.class);
        map.put (Header.ToClient.DISCONNECTION_UP, DisconnectionUP.class);
        map.put (Header.ToClient.GAME_OVER_UP, GameOverUpdate.class);
        map.put (Header.ToClient.GENERIC_INFO, GenericInfoUpdate.class);
        return map;
    }

    public static Class<? extends ViewUpdate> getUpdateType(Header.ToClient toClient) {
        return map.get(toClient) != null ? map.get (toClient) : null;
    }
}
