package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.model.gamelogic.actions.ActionFactory;
import it.polimi.ingsw.utils.network.Header;
import java.util.HashMap;

public class CommandFactory {
    private static HashMap<Header.ToServer, Class<? extends Command>> map = init ();

    private static HashMap<Header.ToServer, Class<? extends Command>> init() {
        map = new HashMap<> ();
        map.put (Header.ToServer.NEW_USER, NewUserCommand.class);
        map.put (Header.ToServer.SET_NUM_PLAYERS, SetNumPlayersCommand.class);
        map.put (Header.ToServer.SET_USERNAME, UsernameCommand.class);
        map.put (Header.ToServer.START_MATCH, StartMatchCommand.class);
        map.put (Header.ToServer.NEW_ROOM, NewRoomCommand.class);
        map.put (Header.ToServer.EXISTING_ROOM, ExistingRoomConnectionCommand.class);
        return map;
    }

    public static Class<? extends Command> getCommandType(Header.ToServer toServer) {
        return map.get(toServer) != null ? map.get (toServer) :
                ActionFactory.getActionType(toServer);
    }
}
