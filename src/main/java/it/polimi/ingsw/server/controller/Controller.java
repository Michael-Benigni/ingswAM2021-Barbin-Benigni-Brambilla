package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.exception.*;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.GameFactory;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.utils.Entry;
import it.polimi.ingsw.utils.config.ServerPrefs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private final ArrayList<Entry<WaitingRoom, Game>> waitingRooms;

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
        return null;
    }

    private WaitingRoom getWaitingRoomOf(User user) throws InvalidUserException {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getKey ().contains(user))
                return entry.getKey ();
        }
        throw new InvalidUserException ();
    }


    public synchronized void startMatch(User user) throws IllegalNumberOfPlayersException, InvalidUserException, TooManyPlayersException, FileNotFoundException, EmptyDeckException, OnlyLeaderCanStartGameException, WrongBoardException, CellNotFoundInFaithTrackException {
        WaitingRoom room = getWaitingRoomOf (user);
        if (room.getLeader () == user) {
            if (room.isFull ()) {
                GameFactory factory = new GameFactory ();
                Game game = factory.MultiOrSingleplayerGame (room.getSize ());
                addGameToRoom(game, room);
                ArrayList<User> users = room.getAllUsers ();
                for (User userInRoom : users) {
                    Player player = game.createPlayer ();
                    room.setPlayerOf (userInRoom, player);
                    player.attach (userInRoom.getView ());
                    game.attach (userInRoom.getView ());
                    player.setUsername (userInRoom.getUsername ());
                }
                factory.setup (game);
            }
            else
                throw new IllegalNumberOfPlayersException ();
        }
        else
            throw new OnlyLeaderCanStartGameException ();
    }

    private void addGameToRoom(Game game, WaitingRoom room) {
        this.waitingRooms.forEach (entry -> {
            if(entry.getKey ().equals (room))
                entry.setValue (game);
        });
    }

    public synchronized void setWaitingRoomDimension(User user, int newDim) throws InvalidUserException, ImpossibleChangingSizeException {
        WaitingRoom room = getWaitingRoomOf (user);
        room.setSize (newDim, user);
    }

    public synchronized void registerToNewRoom(User user) throws FullWaitingRoomException, InvalidUserException {
        newWaitingRoom ().put (user);
    }

    private WaitingRoom newWaitingRoom() {
        WaitingRoom room = new WaitingRoom (ServerPrefs.getMaxUsersInWaitingRoom ());
        waitingRooms.add (new Entry<> (room, null));
        return room;
    }

    private void addToLastWaitingRoom(User user) throws FullWaitingRoomException, InvalidUserException, FirstWaitingRoomException {
        if (!waitingRooms.isEmpty ()) {
            WaitingRoom room = getFirstNotFullWaitingRoom();
            room.put (user);
        }
        else
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
            addToLastWaitingRoom (user);
        } catch (FullWaitingRoomException | FirstWaitingRoomException e) {
            newWaitingRoom ();
            register (user);
        }
    }

    public synchronized void handleCommandOf(User user, Command command) throws Exception {
        command.handled (this, user);
    }

    public synchronized void handleMatchMoveOf (User user, Action action) throws Exception {
        Game game = getGameOf(user);
        try {
            game.performActionOf (this.getWaitingRoomOf (user).getPlayerOf (user), action);
        } catch (EndGameException e) {
            System.out.printf ("The Game is ended. Room %d closed\n", this.waitingRooms.indexOf (getWaitingRoomOf (game)));
            onGameEnded (getWaitingRoomOf(game));
        }
    }

    private WaitingRoom getWaitingRoomOf(Game game) {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getValue ().equals (game))
                return entry.getKey ();
        }
        return null;
    }

    public synchronized void disconnect(User user) throws InvalidUserException {
        WaitingRoom room = getWaitingRoomOf (user);
        try {
            room.disconnect (user, getGameOf (user));
        } catch (EmptyWaitingRoomException e) {
            Entry<WaitingRoom, Game> entry = getEntryOf(getWaitingRoomOf (user));
            this.waitingRooms.remove (entry);
        } catch (EndGameException e) {
            System.out.printf ("No more player connected to the waiting room n° %d. Room closed\n", this.waitingRooms.indexOf (getEntryOf (room)));
            onGameEnded (room);
        }
    }

    private Entry<WaitingRoom, Game> getEntryOf(WaitingRoom room) {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            if (entry.getKey ().equals (room))
                return entry;
            return null;
    }

    public synchronized void registerToWaitingRoomWith(int ID, User user) throws FullWaitingRoomException, InvalidUserException {
        for (WaitingRoom room : allRooms ())
            if (room.getID () == ID) {
                try {
                    room.reconnection (user, getGameOf(room));
                } catch (EndGameException e) {
                    onGameEnded(room);
                }
            }
    }

    private void onGameEnded(WaitingRoom room) {
        this.waitingRooms.remove (getEntryOf (room));
    }

    private Game getGameOf(WaitingRoom room) {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            if (entry.getKey ().equals (room))
                return this.waitingRooms.get (this.waitingRooms.indexOf (entry)).getValue ();
            return null;
    }

    private ArrayList<WaitingRoom> allRooms() {
        ArrayList<WaitingRoom> rooms = new ArrayList<> ();
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            rooms.add (entry.getKey ());
        return rooms;
    }
}
