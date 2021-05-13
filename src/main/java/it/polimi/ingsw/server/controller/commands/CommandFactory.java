package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.model.gamelogic.actions.ActionFactory;
import it.polimi.ingsw.server.utils.network.Header;

import java.util.HashMap;

public class CommandFactory {
    private static HashMap<Header, Class<? extends Command>> map = init ();

    private static HashMap<Header, Class<? extends Command>> init() {
        /*map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );*/
        return map;
    }

    public static Class<? extends Command> getCommandType(Header header) {
        HashMap<Header, Class<? extends Command>> map = new HashMap<> ();
        return map.get(header) != null ? map.get (header) :
                ActionFactory.getActionType(header);
    }
}
