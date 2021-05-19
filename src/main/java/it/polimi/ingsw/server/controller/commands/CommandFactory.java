package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.model.gamelogic.actions.ActionFactory;
import it.polimi.ingsw.utils.network.ToServer;

import java.util.HashMap;

public class CommandFactory {
    private static HashMap<ToServer, Class<? extends Command>> map = init ();

    private static HashMap<ToServer, Class<? extends Command>> init() {
        map = new HashMap<> ();
        map.put (ToServer.NEW_USER, NewUserCommand.class);
        map.put (ToServer.SET_NUM_PLAYERS, SetNumPlayersCommand.class);
        map.put (ToServer.SET_USERNAME, UsernameCommand.class);
        /*map.put (ToServer, );
        map.put (ToServer, );
        map.put (ToServer, );
        map.put (ToServer, );*/
        return map;
    }

    public static Class<? extends Command> getCommandType(ToServer toServer) {
        Class<? extends Command> commandClass = map.get(toServer);
        return map.get(toServer) != null ? map.get (toServer) :
                ActionFactory.getActionType(toServer);
    }
}
