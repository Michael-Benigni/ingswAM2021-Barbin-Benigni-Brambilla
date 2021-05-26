package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.utils.network.Header;
import java.util.HashMap;

public class UpdateFactory {
    private static HashMap<Header.ToClient, Class<? extends ViewUpdate>> map = init ();

    private static HashMap<Header.ToClient, Class<? extends ViewUpdate>> init() {
        map = new HashMap<> ();
        map.put (Header.ToClient.USER_REGISTERED, UserRegisteredUP.class);
        map.put (Header.ToClient.YOUR_TURN, YourTurnUP.class);
        map.put (Header.ToClient.TURN_POSITION_UPDATE, TurnPositionUP.class);
        map.put (Header.ToClient.FULL_ROOM, WaitingRoomFullUP.class);
        return map;
    }

    public static Class<? extends ViewUpdate> getUpdateType(Header.ToClient toClient) {
        return map.get(toClient) != null ? map.get (toClient) : null;
    }
}
