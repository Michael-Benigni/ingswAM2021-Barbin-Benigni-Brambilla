package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoMatch {
    private int roomID;
    private String yourUsername;
    private int numPlayerInTurn;
    private int waitingRoomSize;
    private HashMap<Integer, String> positionsAndPlayers;
    private boolean isLeader;

    public boolean isLeader() {
        return isLeader;
    }

    public InfoMatch() {
        this.positionsAndPlayers = new HashMap<> ();
    }

    public int getNumPlayerInTurn() {
        return numPlayerInTurn;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setYourUsername(String yourUsername) {
        this.yourUsername = yourUsername;
    }

    public void setNumPlayerInTurn(int numPlayerInTurn) {
        this.numPlayerInTurn = numPlayerInTurn;
    }

    public void setWaitingRoomSize(int waitingRoomSize) {
        this.waitingRoomSize = waitingRoomSize;
    }

    public String getYourUsername() {
        return yourUsername;
    }

    public ArrayList<String> getOtherPlayersUsernames() {
        return new ArrayList<> (positionsAndPlayers.values ());
    }

    public String getPlayerAt(int positionInGame){
        String player = positionsAndPlayers.get (positionInGame);
        if (player == null)
            player = yourUsername;
        return player;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getWaitingRoomSize() {
        return waitingRoomSize;
    }

    public int getNumCurrentPlayersInRoom(){
        return positionsAndPlayers.size();
    }

    public void putNewPlayer(int position, String name) {
        positionsAndPlayers.put (position, name);
    }

    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }
}
