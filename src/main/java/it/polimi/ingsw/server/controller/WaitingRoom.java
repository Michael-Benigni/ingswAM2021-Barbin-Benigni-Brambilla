package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.exception.FullWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class WaitingRoom {
    private int size;
    private final HashMap<User, Player> usersPlayers;
    private User leader;
    private static int numOfWaitingRooms = 0;
    private final int ID;

    public WaitingRoom(int size) {
        this.size = size;
        this.usersPlayers = new HashMap<> ();
        this.ID = numOfWaitingRooms;
        numOfWaitingRooms++;
    }

    public void put(User key) throws InvalidUserException, FullWaitingRoomException {
        if (this.usersPlayers.size () < this.size) {
            if (key.getUsername() != null && isUnique (key)) {
                if (this.usersPlayers.isEmpty ())
                    leader = key;
                this.usersPlayers.put (key, null);
            } else
                throw new InvalidUserException ();
        } else
            throw new FullWaitingRoomException ();
    }

    private boolean isUnique (User user) {
        for (User key : this.usersPlayers.keySet()) {
            if (user.sameUsername (key))
                return false;
            }
        return true;
    }

    int getSize() {
        return size;
    }

    void setPlayerOf (User user, Player player) {
        this.usersPlayers.remove (user);
        this.usersPlayers.put (user, player);
    }


    boolean contains(User user) {
        for (User user1 : usersPlayers.keySet ())  {
            if (user == user1)
                return true;
            }
        return false;
    }

    public void setSize(int size, User leader) throws ImpossibleChangingSizeException {
        if (this.usersPlayers.size () < size && this.leader == leader)
            this.size = size;
        else
            throw new ImpossibleChangingSizeException ();
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<> (this.usersPlayers.keySet ());
    }

    public boolean isFull() {
        return this.size == usersPlayers.size ();
    }

    public Player getPlayerOf(User user) {
        return this.usersPlayers.get (user);
    }

    public int getID() {
        return this.ID;
    }

    User getLeader() {
        return this.leader;
    }

    public void disconnect(User user) {
        if (contains (user)) {
            Player player = this.usersPlayers.get (user);
            if (player.isConnected ())
                player.setIsConnected (false);
            else {
                this.usersPlayers.remove (user);
                if (leader == user) {
                    ArrayList<User> users = new ArrayList<> (this.usersPlayers.keySet ());
                    if (!this.usersPlayers.isEmpty ())
                        leader = users.get (0);
                    else
                        leader = null;
                }
            }
        }
    }

    public void reconnection(User user) throws FullWaitingRoomException, InvalidUserException {
        if (contains (user))
            usersPlayers.get (user).setIsConnected(true);
        else
            put (user);
    }
}
