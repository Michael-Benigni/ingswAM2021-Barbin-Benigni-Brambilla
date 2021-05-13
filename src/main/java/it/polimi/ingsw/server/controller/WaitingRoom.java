package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.exception.FullWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class WaitingRoom {
    private int size;
    private HashMap<User, Player> usersPlayers;
    private User leader;

    public WaitingRoom(int size) {
        this.size = size;
        this.usersPlayers = new HashMap<> ();
    }

    public void put(User key) throws InvalidUserException, FullWaitingRoomException {
        if (this.getSize() < usersPlayers.size()) {
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
        if (this.size <= size && this.leader == leader)
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
}
