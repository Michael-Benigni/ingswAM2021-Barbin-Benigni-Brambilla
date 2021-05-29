package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.exception.FirstWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.FullWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.GameFactory;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.utils.Entry;
import it.polimi.ingsw.utils.config.Prefs;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Entry<WaitingRoom, Game>> waitingRooms;

    public Controller() {
        this.waitingRooms = new ArrayList<>();
    }

    private Game getGameOf(User user) throws InvalidUserException {
        WaitingRoom room = getWaitingRoomOf (user);
        for (Entry<WaitingRoom, Game> entry : waitingRooms) {
            if (entry.getKey () == room) {
                return entry.getValue ();
            }
        }
        throw new InvalidUserException ();
    }

    private WaitingRoom getWaitingRoomOf(User user) throws InvalidUserException {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getKey ().contains(user))
                return entry.getKey ();
        }
        throw new InvalidUserException ();
    }


    public synchronized void startMatch(User lastUserAddedInRoom) throws IllegalNumberOfPlayersException, InvalidUserException, TooManyPlayersException, FileNotFoundException, EmptyDeckException {
        WaitingRoom room = getWaitingRoomOf (lastUserAddedInRoom);
        if (room.isFull ()) {
            GameFactory factory = new GameFactory ();
            Game game = factory.MultiOrSingleplayerGame (room.getSize ());
            ArrayList<User> users = room.getAllUsers ();
            for (User userInRoom : users) {
                Player player = game.createPlayer ();
                room.setPlayerOf (userInRoom, player);
                player.attach (userInRoom.getView ());
                game.attach (userInRoom.getView ());
                // TODO: evaluate if the game has to begin when the room is full, or uf the start game must be triggered by the client
            }
            factory.setup (game);
        }
    }

    public synchronized void setWaitingRoomDimension(User user, int newDim) throws InvalidUserException, ImpossibleChangingSizeException, IllegalNumberOfPlayersException, TooManyPlayersException, FileNotFoundException, EmptyDeckException {
        WaitingRoom room = getWaitingRoomOf (user);
        room.setSize (newDim, user);
    }

    private void newWaitingRoom() {
        waitingRooms.add (new Entry(new WaitingRoom (Prefs.getMaxUsersInWaitingRoom ()), null));
    }

    private WaitingRoom addToLastWaitingRoom(User user) throws FullWaitingRoomException, InvalidUserException, FirstWaitingRoomException {
        if (!waitingRooms.isEmpty ()) {
            WaitingRoom room = getFirstNotFullWaitingRoom();
            room.put (user);
            return room;
        }
        throw new FirstWaitingRoomException ();
    }

    private WaitingRoom getFirstNotFullWaitingRoom() {
        for (Entry<WaitingRoom, Game> waitingRoom : this.waitingRooms) {
            WaitingRoom room = waitingRoom.getKey ();
            if (!room.isFull ())
                return room;
        }
        return this.waitingRooms.get (waitingRooms.size () - 1).getKey ();
    }


    public synchronized void register(User user) throws InvalidUserException {
        try {
            WaitingRoom room = addToLastWaitingRoom (user);
            notifyRegistration(room, user);
        } catch (FullWaitingRoomException | FirstWaitingRoomException e) {
            newWaitingRoom ();
            register (user);
        }
    }

    private void notifyRegistration(WaitingRoom room, User user) throws InvalidUserException {
        user.getView ().onChanged (getUserInfo (user, true));
        for (User u : getWaitingRoomOf (user).getAllUsers ()) {
            if (u != user)
                u.getView ().onChanged (getUserInfo (user, false));
        }
        if (room.isFull ()) {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToClient.FULL_ROOM);
            room.getLeader ().getView ().onChanged (writer.write ());
        }
    }

    private Sendable getUserInfo(User user, boolean areYou) throws InvalidUserException {
        MessageWriter writer = new MessageWriter();
        writer.setHeader (Header.ToClient.USER_REGISTERED);
        writer.addProperty ("numUsers", getWaitingRoomOf (user).getAllUsers ().size ());
        writer.addProperty ("username", user.getUsername ());
        writer.addProperty ("areYou", areYou);
        writer.addProperty ("numWaitingRoom", getWaitingRoomOf (user).getID());
        return writer.write ();
    }

    public synchronized void handleCommandOf(User user, Command command) throws Exception {
        command.handled (this, user);
    }

    public void handleMatchMoveOf (User user, Action action) throws Exception {
        getGameOf(user).performCommandOf(this.getWaitingRoomOf (user).getPlayerOf (user), action);
    }

    public synchronized void disconnect(User user) throws InvalidUserException {
        Game game = getGameOf (user);
        if (game!= null && !game.isGameIsStarted ())
            getWaitingRoomOf (user).disconnect (user);
        else {
            Player player = getWaitingRoomOf (user).getPlayerOf (user);
            if (player != null && player.isConnected ())
                player.setIsConnected (false);
        }
    }

    public void registerToWaitingRoomWith(int ID, User user) throws FullWaitingRoomException, InvalidUserException {
        for (WaitingRoom room : allRooms ())
            if (room.getID () == ID)
                room.reconnection (user);
    }

    private ArrayList<WaitingRoom> allRooms() {
        ArrayList<WaitingRoom> rooms = new ArrayList<> ();
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            rooms.add (entry.getKey ());
        return rooms;
    }
}
