package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.model.gamelogic.actions.ActionFactory;
import it.polimi.ingsw.server.utils.network.Header;

import java.util.HashMap;

public class CommandFactory {
    private static HashMap<Header, Class<? extends Command>> map = init ();

    private static HashMap<Header, Class<? extends Command>> init() {
        map = new HashMap<> ();
        map.put (Header.NEW_USER, NewUserCommand.class);
        map.put (Header.SET_NUM_PLAYERS, SetNumPlayersCommand.class);
        map.put (Header.SET_USERNAME, UsernameCommand.class);
        /*map.put (Header, );
        map.put (Header, );
        map.put (Header, );
        map.put (Header, );*/
        return map;
    }

    public static Class<? extends Command> getCommandType(Header header) {
        Class<? extends Command> commandClass = map.get(header);
        return map.get(header) != null ? map.get (header) :
                ActionFactory.getActionType(header);
    }
}
