package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.model.gamelogic.actions.ActionFactory;
import it.polimi.ingsw.utils.network.Header;
import java.util.HashMap;

/**
 * Class that contains the map of all the possible initial commands.
 */
public class CommandFactory {
    private static HashMap<Header.ToServer, Class<? extends Command>> map = init ();

    /**
     * Method that initializes the map of all possible initial commands.
     */
    private static HashMap<Header.ToServer, Class<? extends Command>> init() {
        HashMap<Header.ToServer, Class<? extends Command>> map = new HashMap<> ();
        map.put (Header.ToServer.NEW_USER, NewUserCommand.class);
        map.put (Header.ToServer.SET_NUM_PLAYERS, SetNumPlayersCommand.class);
        map.put (Header.ToServer.SET_USERNAME, UsernameCommand.class);
        map.put (Header.ToServer.START_MATCH, StartMatchCommand.class);
        map.put (Header.ToServer.NEW_ROOM, NewRoomCommand.class);
        map.put (Header.ToServer.EXISTING_ROOM, ExistingRoomConnectionCommand.class);
        return map;
    }

    /**
     * Getter method for a specific command in the map. It receives in input an "Header" with subtype "ToServer", then
     * if there is a line in the map with key equals to the provided header, this method returns the class of the
     * initial commands, otherwise it calls the getter method of "Action Factory" (the provided Header could be an
     * initial command or a game action).
     */
    public static Class<? extends Command> getCommandType(Header.ToServer toServer) {
        return map.get(toServer) != null ? map.get (toServer) :
                ActionFactory.getActionType(toServer);
    }
}
