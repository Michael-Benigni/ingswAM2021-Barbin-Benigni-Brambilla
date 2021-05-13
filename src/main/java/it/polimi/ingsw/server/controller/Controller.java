package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.exception.FirstWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.FullWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.GameFactory;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.utils.config.Prefs;
import it.polimi.ingsw.server.utils.network.Message;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class Controller {
    private ArrayList<Entry<WaitingRoom, Game>> waitingRooms;

    public Controller() {
        this.waitingRooms = new ArrayList<>();
    }

    public Game getGameOf(User user) throws InvalidUserException {
        WaitingRoom room = getWaitingRoomOf (user);
        for (Entry<WaitingRoom, Game> entry : waitingRooms) {
            if (entry.getKey () == room) {
                return entry.getValue ();
            }
        }
        throw new InvalidUserException ();
    }

    public WaitingRoom getWaitingRoomOf(User user) throws InvalidUserException {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getKey ().contains(user))
                return entry.getKey ();
        }
        throw new InvalidUserException ();
    }

    private void startMatch(User user) throws IllegalNumberOfPlayersException, InvalidUserException, TooManyPlayersException, FileNotFoundException {
        WaitingRoom room = getWaitingRoomOf (user);
        if (room.isFull ()) {
            GameFactory factory = new GameFactory ();
            Game game = factory.MultiOrSingleplayerGame (room.getSize ());
            ArrayList<User> users = room.getAllUsers ();
            for (User userInRoom : users) {
                Player player = game.createPlayer ();
                room.setPlayerOf (userInRoom, player);
            }
            factory.setup (game);
        }
    }

    public void setWaitingRoomDimension(User user, int newDim) throws InvalidUserException, ImpossibleChangingSizeException, IllegalNumberOfPlayersException, TooManyPlayersException, FileNotFoundException {
        WaitingRoom room = getWaitingRoomOf (user);
        room.setSize (newDim, user);
        if (room.isFull ())
            startMatch (user);
    }

    private void newWaitingRoom() {
        waitingRooms.add (new Entry(new WaitingRoom (Prefs.getMaxUsersInWaitingRoom ()), null));
    }

    private WaitingRoom addToLastWaitingRoom(User user) throws FullWaitingRoomException, InvalidUserException, FirstWaitingRoomException {
        if (!waitingRooms.isEmpty ()) {
            WaitingRoom room = this.waitingRooms.get (waitingRooms.size () - 1).getKey ();
            room.put (user);
            return room;
        }
        throw new FirstWaitingRoomException ();
    }

    public void register(User user) throws FileNotFoundException, InvalidUserException {
        try {
            WaitingRoom room = addToLastWaitingRoom (user);
            if (room.isFull ())
                startMatch (user);
        } catch (FullWaitingRoomException | TooManyPlayersException | IllegalNumberOfPlayersException | FirstWaitingRoomException e) {
            newWaitingRoom ();
            register (user);
        }
    }

    public void handleCommandOf(User user, Message message) throws FileNotFoundException, InvalidUserException, IllegalNumberOfPlayersException, TooManyPlayersException, ImpossibleChangingSizeException {
        message.getCommand ().handled (this, user);
    }

    private class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *                               required to, throw this exception if the entry has been
         *                               removed from the backing map.
         */
        @Override
        public K getKey() {
            return this.key;
        }

        /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * {@code remove} operation), the results of this call are undefined.
         *
         * @return the value corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *                               required to, throw this exception if the entry has been
         *                               removed from the backing map.
         */
        @Override
        public V getValue() {
            return this.value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  (Writes through to the map.)  The
         * behavior of this call is undefined if the mapping has already been
         * removed from the map (by the iterator's {@code remove} operation).
         *
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         * @throws UnsupportedOperationException if the {@code put} operation
         *                                       is not supported by the backing map
         * @throws ClassCastException            if the class of the specified value
         *                                       prevents it from being stored in the backing map
         * @throws NullPointerException          if the backing map does not permit
         *                                       null values, and the specified value is null
         * @throws IllegalArgumentException      if some property of this value
         *                                       prevents it from being stored in the backing map
         * @throws IllegalStateException         implementations may, but are not
         *                                       required to, throw this exception if the entry has been
         *                                       removed from the backing map.
         */
        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }


}
