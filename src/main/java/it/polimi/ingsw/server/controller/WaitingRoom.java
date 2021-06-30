package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.exception.EmptyWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.FullWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.EndGameException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class that represents collection of rooms in which the players can wait the start of the game.
 */
public class WaitingRoom {

    private int size;
    private final HashMap<User, Player> usersPlayers;
    private User leader;
    private static int numOfWaitingRooms = 0;
    private final int ID;

    /**
     * Constructor method of this class. It receives the number of players of this match.
     */
    public WaitingRoom(int size) {
        this.size = size;
        this.usersPlayers = new HashMap<> ();
        this.ID = numOfWaitingRooms;
        numOfWaitingRooms++;
    }

    /**
     * Method that adds a new user in the map "usersPlayers". The added user is still not linked to a player (player is null).
     */
    public void put(User key) throws InvalidUserException, FullWaitingRoomException {
        if (this.usersPlayers.size () < this.size) {
            if (key.getUsername() != null && isUnique (key)) {
                if (this.usersPlayers.isEmpty ())
                    leader = key;
                this.usersPlayers.put (key, null);
                notifyRegistration (key);
                usersPlayers.keySet ().stream ()
                        .filter (user -> user != key)
                        .forEach (user -> user.getView ().onChanged (getUserInfo (key)));
            } else
                throw new InvalidUserException();
        } else
            throw new FullWaitingRoomException();
    }

    private Sendable getUserInfo(User key) {
        MessageWriter writer = new MessageWriter();
        writer.setHeader (Header.ToClient.USER_INFO_UP);
        writer.addProperty ("name", key.getUsername ());
        return writer.write ();
    }

    /**
     * Method that return a boolean -> false if the received user is contained in the key set of the map "userPlayers".
     */
    private boolean isUnique (User user) {
        for (User key : this.usersPlayers.keySet()) {
            if (user.sameUsername (key))
                return false;
            }
        return true;
    }

    /**
     * Getter method for the size of this waiting room.
     */
    int getSize() {
        return size;
    }

    /**
     * Method that adds a new line of the map with the provided user and player.
     */
    void setPlayerOf (User user, Player player) {
        this.usersPlayers.remove (user);
        this.usersPlayers.put (user, player);
    }

    /**
     * Method that returns a boolean -> true if the received user is contained in the map "usersPlayers".
     */
    boolean contains(User user) {
        for (User user1 : usersPlayers.keySet ())  {
            if (user == user1)
                return true;
            }
        return false;
    }

    /**
     * Method that updates the size of this waiting room, but only if the provided user is the leader of this room.
     * Then if this room is full, it throws a method to notify that the room is full and the match can be started.
     */
    public void setSize(int size, User leader) throws ImpossibleChangingSizeException {
        if (this.usersPlayers.size () <= size && this.leader == leader) {
            this.size = size;
            notifyChangedRoomSize(leader, size);
            if (isFull ())
                notifyFullRoom ();
        }
        else
            throw new ImpossibleChangingSizeException();
    }

    /**
     * Method that prints a notification about the updating of the size of this room.
     */
    private void notifyChangedRoomSize(User leader, int size) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.SET_NUM_PLAYERS);
        writer.addProperty ("numPlayers", size);
        leader.getView ().onChanged (writer.write ());
    }

    /**
     * Getter method for the array of users in this room.
     */
    public ArrayList<User> getAllUsers() {
        return new ArrayList<> (this.usersPlayers.keySet ());
    }

    /**
     * Method that returns a boolean -> true if this room is full.
     */
    public boolean isFull() {
        return this.size == usersPlayers.size ();
    }

    /**
     * Getter method for the player linked to the provided user.
     */
    public Player getPlayerOf(User user) {
        return this.usersPlayers.get (user);
    }

    /**
     * Getter method for the identification number of this room.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Getter method for the user that creates this room.
     */
    User getLeader() {
        return this.leader;
    }

    /**
     * Method that delete the provided user from this room.
     */
    public void disconnect(User user, Game game) throws EmptyWaitingRoomException, EndGameException {
        if (contains (user)) {
            Player player = this.usersPlayers.get (user);
            if (player != null && player.isConnected ()) {
                player.setIsConnected (false, game);
                game.detach (user.getView ());
            }
            else {
                this.usersPlayers.remove (user);
                if (leader == user) {
                    ArrayList<User> users = new ArrayList<> (this.usersPlayers.keySet ());
                    if (!this.usersPlayers.isEmpty ()) {
                        leader = users.get (0);
                        leader.getView ().onChanged (newLeaderUpdate());
                    }
                    else {
                        leader = null;
                        throw new EmptyWaitingRoomException ();
                    }
                }
            }
            usersPlayers.keySet ().stream ().filter ((u) -> u != user).forEach ((u) ->
                    u.getView().onChanged (getDisconnectionUpdate(user)));
        }
    }

    /**
     * Method that updates the leader of this room in case of the precedent one has a disconnection.
     */
    private Sendable newLeaderUpdate() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.NEW_LEADER_UPDATE);
        return writer.write ();
    }

    /**
     * Method that prints to the players the notification about the disconnection of one player.
     */
    private Sendable getDisconnectionUpdate(User user) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.DISCONNECTION_UP);
        writer.addProperty ("playerDisconnected", user.getUsername ());
        return writer.write ();
    }

    /**
     * Method that restores the attributes and the view of an user that reconnects after a disconnection.
     */
    public boolean reconnection(User user, Game game) throws FullWaitingRoomException, InvalidUserException, EndGameException {
        boolean isReconnection = false;
        if ((contains (user) || !isUnique (user)) && game != null) {
            User oldUser = findOldUserWithUsername (user.getUsername ());
            Player player = this.usersPlayers.get (oldUser);
            this.usersPlayers.remove (oldUser);
            put(user);
            setPlayerOf (user, player);
            player.attach (user.getView ());
            sendReconnectionMessageOf (user);
            usersPlayers.get (user).setIsConnected (true, game);
            game.reconnectionOf (player);
            isReconnection = true;
        } else
            put (user);
        return isReconnection;
    }

    /**
     * Getter method for an user, with its username provided in input. If doesn't exist any user with that username, this method returns null.
     */
    private User findOldUserWithUsername(String username) {
        for (User user : this.usersPlayers.keySet ()) {
            if (user.getUsername ().equals (username))
                return user;
        }
        return null;
    }

    /**
     * Method used to print to the view the notification about the connection of a new player in this waiting room.
     * If the players are enough to start the game, this method notify the leader that the game can be started.
     */
    private void notifyRegistration(User user) {
        user.getView ().onChanged (getInfoRoomFor (user));
        if (isFull ())
            notifyFullRoom();
    }

    /**
     * Method used to send a notification to the leader of this game about the reaching of the specified number of players.
     */
    private void notifyFullRoom() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.FULL_ROOM);
        getLeader ().getView ().onChanged (writer.write ());
    }

    /**
     * Getter method used to print in the view the information contained in this waiting room.
     */
    private Sendable getInfoRoomFor(User user) {
        MessageWriter writer = new MessageWriter();
        writer.setHeader (Header.ToClient.USER_REGISTERED);
        writer.addProperty ("numUser", getAllUsers ().size ());
        writer.addProperty ("numUsers", size);
        writer.addProperty ("isLeader", user == leader);
        writer.addProperty ("username", user.getUsername ());
        writer.addProperty ("numWaitingRoom", getID());
        return writer.write ();
    }

    /**
     * Method used to send a notification to the other players that the one provided in input is reconnected into the match.
     */
    public void sendReconnectionMessageOf(User user) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.RECONNECTION_RESPONSE);
        writer.addProperty ("playerPosition", this.usersPlayers.get (user).getPosition ());
        this.usersPlayers.keySet ().forEach ((u) -> u.getView ().onChanged (writer.write ()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaitingRoom)) return false;
        WaitingRoom room = (WaitingRoom) o;
        return getSize () == room.getSize () && getID () == room.getID () && usersPlayers.equals (room.usersPlayers) && getLeader ().equals (room.getLeader ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getSize (), usersPlayers, getLeader (), getID ());
    }
}
