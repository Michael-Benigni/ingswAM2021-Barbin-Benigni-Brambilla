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

/**
 * Fundamental class of the "controller" package. It connects the view to the model.
 */
public class Controller {
    /**
     * Collection of waiting rooms. The arraylist of entry is better than a common map in this case, because it's
     * important the order of the waiting rooms (controller tries to complete the oldest rooms before the last ones).
     */
    private final ArrayList<Entry<WaitingRoom, Game>> waitingRooms;

    /**
     * Constructor method of this class. It builds an empty array of entry.
     */
    public Controller() {
        this.waitingRooms = new ArrayList<>();
    }

    /**
     * Getter method for the game where the provided user is registered.
     */
    private Game getGameOf(User user) throws InvalidUserException {
        WaitingRoom room = getWaitingRoomOf (user);
        for (Entry<WaitingRoom, Game> entry : waitingRooms) {
            if (entry.getKey () == room) {
                return entry.getValue ();
            }
        }
        return null;
    }

    /**
     * Getter method for the waiting room of the game where the provided user is registered.
     */
    private WaitingRoom getWaitingRoomOf(User user) throws InvalidUserException {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getKey ().contains(user))
                return entry.getKey ();
        }
        throw new InvalidUserException ();
    }

    /**
     * Method that starts the game if the waiting room is full.
     */
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

    /**
     * Method that adds the provided game to the entry that has key equals to the provided waiting room.
     */
    private void addGameToRoom(Game game, WaitingRoom room) {
        this.waitingRooms.forEach (entry -> {
            if(entry.getKey ().equals (room))
                entry.setValue (game);
        });
    }

    /**
     * Method that updates the dimension of the waiting room of the provided user with the provided integer.
     */
    public synchronized void setWaitingRoomDimension(User user, int newDim) throws InvalidUserException, ImpossibleChangingSizeException {
        WaitingRoom room = getWaitingRoomOf (user);
        room.setSize (newDim, user);
    }

    /**
     * Method that adds the provided user to a just created waiting room.
     */
    public synchronized void registerToNewRoom(User user) throws FullWaitingRoomException, InvalidUserException {
        newWaitingRoom ().put (user);
    }

    /**
     * Method that creates a new waiting room and returns it.
     */
    private WaitingRoom newWaitingRoom() {
        WaitingRoom room = new WaitingRoom (ServerPrefs.getMaxUsersInWaitingRoom ());
        waitingRooms.add (new Entry<> (room, null));
        return room;
    }

    /**
     * Method that adds the provided user to the first not full waiting room, if exists.
     */
    private void addToLastWaitingRoom(User user) throws FullWaitingRoomException, InvalidUserException, FirstWaitingRoomException {
        if (!waitingRooms.isEmpty ()) {
            WaitingRoom room = getFirstNotFullWaitingRoom();
            room.put (user);
        }
        else
            throw new FirstWaitingRoomException ();
    }

    /**
     * Method that searches the first not full waiting room in the array and returns it.
     */
    private WaitingRoom getFirstNotFullWaitingRoom() {
        for (Entry<WaitingRoom, Game> waitingRoom : this.waitingRooms) {
            WaitingRoom room = waitingRoom.getKey ();
            if (!room.isFull ())
                return room;
        }
        return this.waitingRooms.get (waitingRooms.size () - 1).getKey ();
    }

    /**
     * Method that tries to insert the provided user into the first not full waiting room; if all the rooms are full,
     * this methods creates a new room and recalls itself.
     */
    public synchronized void register(User user) throws InvalidUserException {
        try {
            addToLastWaitingRoom (user);
        } catch (FullWaitingRoomException | FirstWaitingRoomException e) {
            newWaitingRoom ();
            register (user);
        }
    }

    /**
     * Method to handle the initial commands sent by the client. It calls the method "handled" that belongs to
     * each class that implements "Command" interface.
     */
    public synchronized void handleCommandOf(User user, Command command) throws Exception {
        command.handled (this, user);
    }

    /**
     * Method to handle the match moves. It calls the "performActionOf" method of the game of the provided user.
     * If the game is over, this method notifies the vie and removes this room from the array.
     */
    public synchronized void handleMatchMoveOf (User user, Action action) throws Exception {
        Game game = getGameOf(user);
        try {
            game.performActionOf (this.getWaitingRoomOf (user).getPlayerOf (user), action);
        } catch (EndGameException e) {
            System.out.printf ("The Game is ended. Room %d closed\n", this.waitingRooms.indexOf (getWaitingRoomOf (game)));
            onGameEnded (getWaitingRoomOf(game));
        }
    }

    /**
     * Getter method for the waiting room linked to the provided game.
     */
    private WaitingRoom getWaitingRoomOf(Game game) {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getValue ().equals (game))
                return entry.getKey ();
        }
        return null;
    }

    /**
     * Method that remove the provided user from the waiting room where it was registered. Then is the room is now empty,
     * it is removed from the array of waiting rooms.
     */
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

    /**
     * Getter method for the specific entry that contains the provided waiting room as value.
     */
    private Entry<WaitingRoom, Game> getEntryOf(WaitingRoom room) {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            if (entry.getKey ().equals (room))
                return entry;
            return null;
    }

    /**
     * Method that allows to the provided user to connect into the waiting room with the provided ID.
     */
    public synchronized void registerToWaitingRoomWith(int ID, User user) throws FullWaitingRoomException, InvalidUserException, WrongIdWaitingRoomException {
        for (WaitingRoom room : allRooms ())
            if (room.getID () == ID) {
                try {
                    room.reconnection (user, getGameOf(room));
                } catch (EndGameException e) {
                    onGameEnded(room);
                }
            }
        throw new WrongIdWaitingRoomException(ID);
    }

    /**
     * Method that remove the provided waiting room from the array of rooms.
     */
    private void onGameEnded(WaitingRoom room) {
        this.waitingRooms.remove (getEntryOf (room));
    }

    /**
     * Getter method for the game linked to the provided room. If it's impossible to find the provided room, this
     * method returns null.
     */
    private Game getGameOf(WaitingRoom room) {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            if (entry.getKey ().equals (room))
                return this.waitingRooms.get (this.waitingRooms.indexOf (entry)).getValue ();
        return null;
    }

    /**
     * Getter method for the array of waiting rooms.
     */
    private ArrayList<WaitingRoom> allRooms() {
        ArrayList<WaitingRoom> rooms = new ArrayList<> ();
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            rooms.add (entry.getKey ());
        return rooms;
    }
}
