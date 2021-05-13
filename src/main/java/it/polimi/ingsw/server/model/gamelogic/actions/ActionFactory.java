package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Action;
import it.polimi.ingsw.server.utils.network.Header;
import java.util.HashMap;

public class ActionFactory {
    private static final HashMap<Header, Class<? extends Action>> map = init ();

    private static HashMap<Header, Class<? extends Action>> init() {
        /*map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );*/
        return map;
    }

    public static Class<? extends Action> getActionType(Header header) {
        HashMap<Header, Class<? extends Action>> map = new HashMap<> ();
        return map.get (header);
    }
}
